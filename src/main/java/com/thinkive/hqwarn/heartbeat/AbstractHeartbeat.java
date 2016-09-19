package com.thinkive.hqwarn.heartbeat;

import com.thinkive.base.util.MyLog;

/**@描述: 心跳机制抽象类
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年8月9日 下午4:03:23
 */
public abstract class AbstractHeartbeat implements Runnable, IHeartBeat
{
    
    /** 心跳线程 */
    private Thread  heartThread;
                    
    /** 是否启动 */
    private boolean isStart;
                    
    /**是否关闭*/
    private boolean isClose;
                    
    /**心跳间隔时间*/
    private long    idleTime;
                    
    public AbstractHeartbeat()
    {
        // TODO Auto-generated constructor stub
        
        isStart = false;
        isClose = false;
        idleTime = 1000;
    }
    
    public AbstractHeartbeat(long idleTime)
    {
        this();
        this.idleTime = idleTime;
    }
    
    public void start()
    {
        
        if ( isClose )
        {
            throw new IllegalStateException("服务已经被关闭");
        }
        
        if ( heartThread == null )
        {
            startThread();
        }
        
        isStart = true;
    }
    
    private synchronized void startThread()
    {
        if ( heartThread == null )
        {
            heartThread = new Thread(this);
            heartThread.start();
        }
    }
    
    public void stop()
    {
        isStart = false;
    }
    
    public void close()
    {
        isClose = true;
    }
    
    public long getIdleTime()
    {
        return idleTime;
    }
    
    public void setIdleTime(long idleTime)
    {
        this.idleTime = idleTime;
    }
    
    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        while ( !isClose)
        {
            try
            {
                while (isStart)
                {
                    long t = System.currentTimeMillis();
                    
                    heartbeat();
                    
                    t = System.currentTimeMillis() - t;
                    
                    try
                    {
                        t = idleTime - t;
                        
                        if ( t > 0 )
                        {
                            Thread.sleep(t);
                        }
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                    }
                }
            }
            catch (Exception e1)
            {
                // TODO Auto-generated catch block
                MyLog.serverLog.error(" --  @心跳:warn    --  ", e1);
            }
            
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
            }
        }
    }
    
}
