package com.thinkive.market.service.conn.push;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @����: ��ȡ���͵� ��������Դʱ��
 * @��Ȩ: Copyright (c) 2015
 * @��˾: Thinkive
 * @����: ���ӿ�
 * @��������: 2015��11��12�� ����3:41:47
 */
public class Updater10010 extends Updater
{
    private static final Log logger = LogFactory.getLog("Server");
                                    
    private static Updater   updater;
                             
    private Updater10010()
    {
    }
    
    public static Updater getInstance()
    {
        if ( updater == null )
            synchronized (Updater10010.class)
            {
                if ( updater == null )
                {
                    updater = new Updater10010();
                    updater.setName("Updater10010");
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
                
            }
            else
                logger.debug("	-- @��������Դʱ�� --  ת������������ݳ��Ȳ�Ϊ 8 ������Ϊ��" + data.length);
                
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            logger.warn("", e);
        }
    }
}
