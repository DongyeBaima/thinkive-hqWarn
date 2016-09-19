package com.thinkive.hqwarn.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.thinkive.base.util.ClassHelper;
import com.thinkive.base.util.DateUtil;
import com.thinkive.base.util.MyLog;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.bean.WarnPushInfo;
import com.thinkive.hqwarn.cache.QueueManager;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.judger.IWarnJudger;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.timerengine.Task;

public class WarnJudgeTask implements Task
{
    private final List<IWarnJudger> judgerList;
                                    
    private final List<Thread>      threadList = new ArrayList<>();
                                               
    public WarnJudgeTask()
    {
        // TODO Auto-generated constructor stub
        judgerList = loadJudgerList();
    }
    
    @Override
    public void execute()
    {
        synchronized (threadList)
        {
            for (int i = threadList.size(); i < ServerConfig.JUDGE_THREAD_COUNT; i++)
            {
                Thread thread = new Thread(new JudgeRunnable(), "WARN-JUDGE-0" + i);
                threadList.add(thread);
                thread.start();
            }
        }
    }
    
    /**
     * @描述: 在默认路径中读取接口处理类
     * @作者: 王嵊俊
     * @创建日期: 2016年5月5日 下午2:30:53
     * @param funcMap
     */
    @SuppressWarnings("rawtypes")
    private List<IWarnJudger> loadJudgerList()
    {
        List<Class> classList = ClassHelper.getAllClassByFather(IWarnJudger.class);
        
        List<IWarnJudger> judgerList = new ArrayList<>();
        
        if ( classList == null )
        {
            return judgerList;
        }
        
        for (Class c : classList)
        {
            try
            {
                judgerList.add((IWarnJudger) c.newInstance());
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
            }
        }
        
        return judgerList;
    }
    
    class JudgeRunnable implements Runnable
    {
        @Override
        public void run()
        {
            // TODO Auto-generated method stub
            
            Stock stock;
            
            while (true)
            {
                try
                {
                    stock = QueueManager.hqQueue.take();
                    
                    Map<Integer, List<WarnInfo>> warnInfoCache = ServerCache.getWarnInfoCache(stock.getStockCode());
                    if ( warnInfoCache == null )
                    {
                        continue;
                    }
                    
                    List<WarnInfo> triggerList = null;
                    
                    WarnPushInfo warnPushInfo = null;
                    String title = stock.getName() + stock.getStockCode();
                    String showTimeStr = DateUtil.FormateDate(stock.getSourceTime(), "yyyy-MM-dd HH:mm");
                    String content = null;
                    
                    for (IWarnJudger judger : judgerList)
                    {
                        triggerList = judger.judger(stock);
                        
                        if ( triggerList == null || triggerList.isEmpty() )
                        {
                            continue;
                        }
                        
                        for (WarnInfo warnInfo : triggerList)
                        {
                            if ( warnInfo.isValid() )
                            {
                                content = judger.getMessageContent(stock, warnInfo);
                                
                                warnInfo.decrementValidCount();
                                
                                warnPushInfo = new WarnPushInfo();
                                warnPushInfo.setStock(stock);
                                warnPushInfo.setWarnInfo(warnInfo);
                                warnPushInfo.setContent(content);
                                warnPushInfo.setTitle(title);
                                warnPushInfo.setShowTimeStr(showTimeStr);
                                warnPushInfo.setMessage("【股价提醒】" + title + " " + content);
                                
                                QueueManager.warnPushQueue.offer(warnPushInfo);
                            }
                        }
                    }
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
