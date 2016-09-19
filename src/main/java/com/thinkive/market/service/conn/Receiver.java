package com.thinkive.market.service.conn;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.thinkive.hqwarn.cache.HQState;

public class Receiver extends Thread
{
    protected static BlockingQueue<byte[]> queue = new LinkedBlockingQueue<byte[]>();
                                                 
    /**
     * 当前处理包的个数
     */
    protected static long                  count = 0;
                                                 
    public static boolean isReceived()
    {
        return HQState.HK.isInit() || HQState.HS.isInit();
    }
    
    /**
     * @描述：获取数据
     * @作者：岳知之
     * @时间：2012-4-28 下午5:02:33
     * @return
     * @throws Exception 
     */
    public static byte[] getData() throws Exception
    {
        return queue.take();
    }
    
    /**
     * @描述：获取缓冲区长度
     * @作者：岳知之
     * @时间：2012-5-8 下午12:21:16
     * @return
     */
    public static int getBufferSize()
    {
        return queue.size();
    }
    
    /**
     * @描述：获取处理包的个数
     * @作者：岳知之
     * @时间：2013-4-9 下午1:53:05
     * @return
     */
    public static long getCount()
    {
        return count;
    }
}
