package com.thinkive.market.service.conn.push;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.thinkive.hqwarn.cache.HQState;

/**
 * @描述: 获取推送的 上海行情源时间
 * @版权: Copyright (c) 2015
 * @公司: Thinkive
 * @作者: 王嵊俊
 * @创建日期: 2015年11月12日 下午3:41:47
 */
public class Updater10011 extends Updater
{
    private static final Log logger = LogFactory.getLog("Server");
                                    
    private static Updater   updater;
                             
    private Updater10011()
    {
    }
    
    public static Updater getInstance()
    {
        if ( updater == null )
            synchronized (Updater10011.class)
            {
                if ( updater == null )
                {
                    updater = new Updater10011();
                    updater.setName("Updater10011");
                    updater.start();
                }
            }
        return updater;
    }
    
    @Override
    protected void update(byte[] data)
    {
        try
        {
            if ( data.length >= 8 )
            {
                ByteBuffer dataBuffer = ByteBuffer.wrap(data);
                dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
                
                long time = dataBuffer.getLong();
                
                HQState.HS.setSourceTime(time);
                
            }
            else
                logger.debug("	-- @上海行情源时间 --  转码机推来的数据长度不为 8 ，长度为：" + data.length);
                
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            logger.warn("", e);
        }
    }
}
