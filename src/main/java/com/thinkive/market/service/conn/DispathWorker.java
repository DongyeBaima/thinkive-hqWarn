package com.thinkive.market.service.conn;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.thinkive.base.util.ClassHelper;
import com.thinkive.base.util.MyStringUtil;
import com.thinkive.market.service.conn.push.Updater;

public class DispathWorker extends Thread
{
    private static Log                  logger     = LogFactory.getLog("Server");
                                                   
    private final Map<Integer, Updater> updaterMap = new HashMap<>();
                                                   
    public DispathWorker()
    {
        setName("ReceiveWorker");
        loadFuncnoClassByDefaultPath(updaterMap);
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                final byte[] data = Receiver.getData();
                
                parseData(data);// 解析推送来的数据 update更新
            }
            catch (Exception e)
            {
                logger.warn("解析推送数据出现异常", e);
            }
        }
    }
    
    private void parseData(byte[] b)
    {
        try
        {
            byte[] data = new byte[b.length];
            byte[] data1 = new byte[b.length - 16];
            
            ByteBuffer dataBuffer = ByteBuffer.wrap(b);// 如下,通过包装的方法创建的缓冲区保留了被包装数组内保存的数据.
            dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
            
            int length = dataBuffer.getInt();// 长度
            int funcno = dataBuffer.getInt();// 功能号
            
            if ( length != data.length )
            {
                logger.info("包长度出错,length:" + length + "，接收长度:" + data.length);
                return;
            }
            dataBuffer.position(16);// 跳过8个字节长度 保留字
            dataBuffer.get(data1);
            
            Updater updater = updaterMap.get(funcno);
            
            if ( updater != null )
            {
                updater.add(data1);
                dataBuffer.clear();
            }
            
        }
        catch (IllegalArgumentException e)
        {
            logger.warn(e.getMessage(), e);
        }
        catch (SecurityException e)
        {
            logger.warn(e.getMessage(), e);
        }
    }
    
    /**
     * @描述: 在默认路径中读取接口处理类
     * @作者: 王嵊俊
     * @创建日期: 2016年5月5日 下午2:30:53
     * @param funcMap
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void loadFuncnoClassByDefaultPath(Map<Integer, Updater> funcMap)
    {
        List<Class> classList = ClassHelper.getAllClassByFather(Updater.class);
        
        if ( classList == null )
        {
            return;
        }
        
        String className = null;
        for (Class c : classList)
        {
            try
            {
                className = c.getSimpleName();
                
                if ( className == null )
                {
                    continue;
                }
                
                if ( className.startsWith("Updater") )
                {
                    className = className.substring(7);
                    
                    int funcNo = MyStringUtil.strToInt(className);
                    
                    if ( funcNo == 0 || funcMap.get(funcNo) != null )
                    {
                        continue;
                    }
                    Method m = c.getMethod("getInstance");
                    funcMap.put(funcNo, (Updater) m.invoke(c));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
}
