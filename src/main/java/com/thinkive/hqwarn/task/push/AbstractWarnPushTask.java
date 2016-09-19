package com.thinkive.hqwarn.task.push;

import java.util.ArrayList;
import java.util.List;

import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.bean.WarnPushInfo;
import com.thinkive.hqwarn.cache.QueueManager;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.hqwarn.util.WarnCacheHelper;
import com.thinkive.timerengine.Task;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;

public abstract class AbstractWarnPushTask implements Task
{
    
    private static final List<Thread> threadList  = new ArrayList<>();
                                                  
    private final ServerState         serverState = BeanFactory.getBean(Key.ServerState);
                                                  
    @Override
    public void execute()
    {
        synchronized (threadList)
        {
            for (int i = threadList.size(); i < ServerConfig.PUSH_THREAD_COUNT; i++)
            {
                Thread thread = new Thread(new pushRunnable(), "PUSHMESSAGE-SEND-0" + i);
                threadList.add(thread);
                thread.start();
            }
        }
    }
    
    protected abstract boolean sendPushMessage(WarnPushInfo pushInfo);
    
    class pushRunnable implements Runnable
    {
        @Override
        public void run()
        {
            // TODO Auto-generated method stub
            WarnPushInfo pushInfo = null;
            int retryCount = ServerConfig.PUSH_RETRY_COUNT;
            while (true)
            {
                try
                {
                    pushInfo = QueueManager.warnPushQueue.take();
                    
                    if ( !serverState.getState().equals(State.working) )
                    {
                        break;
                    }
                    
                    while (retryCount > 0 && !sendPushMessage(pushInfo))
                    {
                        retryCount--;
                        if ( retryCount == 0 )
                        {
                            MyLog.pushLog.warn("	--	@提醒推送	--	消息推送失败，已重试" + ServerConfig.PUSH_RETRY_COUNT + "次；  "
                                    + pushInfo);
                            MyLog.pushLog.warn("");
                        }
                    }
                    WarnInfo warnInfo = pushInfo.getWarnInfo();
                    
                    QueueManager.dbUpdateQueue.put(warnInfo);
                    WarnCacheHelper.destroydWarn(warnInfo);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    MyLog.serverLog.warn("", e);
                }
            }
        }
    }
}
