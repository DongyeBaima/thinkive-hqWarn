package com.thinkive.hqwarn.task;

import java.util.ArrayList;
import java.util.List;

import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.cache.QueueManager;
import com.thinkive.timerengine.Task;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IDBService;

/**
 * @描述: 更新提醒有效次数
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年6月17日 下午3:48:48
 */
public class WarnInfoUpdateTask implements Task
{
    
    @SuppressWarnings("unchecked")
    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        
        List<WarnInfo> list = new ArrayList<>();
        WarnInfo warnInfo = null;
        
        while (true)
        {
            warnInfo = QueueManager.dbUpdateQueue.poll();
            if ( warnInfo == null )
            {
                break;
            }
            
            list.add(warnInfo);
            
            if ( list.size() >= 100 )
            {
                break;
            }
        }
        
        if ( !list.isEmpty() )
        {
            ((IDBService<WarnInfo>) BeanFactory.getBean(Key.NWarnService)).update(list);
        }
    }
    
}
