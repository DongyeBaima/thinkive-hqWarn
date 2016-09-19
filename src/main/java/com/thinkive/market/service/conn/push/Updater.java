package com.thinkive.market.service.conn.push;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Updater extends Thread
{
    private static Log              logger = LogFactory.getLog("Server");
                                           
    protected BlockingQueue<byte[]> queue  = new LinkedBlockingQueue<byte[]>();
                                           
    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                byte[] data = queue.take();
                update(data);
            }
            catch (Exception e)
            {
                logger.warn("", e);
            }
        }
    }
    
    protected abstract void update(byte[] data);
    
    /**
     * @����������в�������
     * @���ߣ���֪֮
     * @ʱ�䣺2013-10-29 ����10:32:02
     * @param data
     */
    public void add(byte[] data)
    {
        queue.offer(data);
    }
}
