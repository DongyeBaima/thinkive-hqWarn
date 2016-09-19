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
                
                parseData(data);// ���������������� update����
            }
            catch (Exception e)
            {
                logger.warn("�����������ݳ����쳣", e);
            }
        }
    }
    
    private void parseData(byte[] b)
    {
        try
        {
            byte[] data = new byte[b.length];
            byte[] data1 = new byte[b.length - 16];
            
            ByteBuffer dataBuffer = ByteBuffer.wrap(b);// ����,ͨ����װ�ķ��������Ļ����������˱���װ�����ڱ��������.
            dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
            
            int length = dataBuffer.getInt();// ����
            int funcno = dataBuffer.getInt();// ���ܺ�
            
            if ( length != data.length )
            {
                logger.info("�����ȳ���,length:" + length + "�����ճ���:" + data.length);
                return;
            }
            dataBuffer.position(16);// ����8���ֽڳ��� ������
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
     * @����: ��Ĭ��·���ж�ȡ�ӿڴ�����
     * @����: ���ӿ�
     * @��������: 2016��5��5�� ����2:30:53
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
