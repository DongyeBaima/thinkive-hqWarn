package com.thinkive.hqwarn.heartbeat;

import com.thinkive.base.util.MyLog;

/**@����: �������Ƴ�����
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��8��9�� ����4:03:23
 */
public abstract class AbstractHeartbeat implements Runnable, IHeartBeat
{
    
    /** �����߳� */
    private Thread  heartThread;
                    
    /** �Ƿ����� */
    private boolean isStart;
                    
    /**�Ƿ�ر�*/
    private boolean isClose;
                    
    /**�������ʱ��*/
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
            throw new IllegalStateException("�����Ѿ����ر�");
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
                MyLog.serverLog.error(" --  @����:warn    --  ", e1);
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
