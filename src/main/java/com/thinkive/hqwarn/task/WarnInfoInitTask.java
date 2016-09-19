package com.thinkive.hqwarn.task;

import java.util.Map;

import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.timerengine.Task;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.impl.NStockWarnService;

/**
 * @描述: 初始化提醒有效次数
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年6月17日 下午3:48:05
 */
public class WarnInfoInitTask implements Task
{
    
    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        MyLog.serverLog.warn("	--	@提醒信息初始化任务	--	任务开始");
        initCacheWarnInfo();
        initDBWarnInfo();
        MyLog.serverLog.warn("	--	@提醒信息初始化任务	--	任务完成");
    }
    
    /**
     * @描述: 初始化缓存提醒有效次数,重新缓存 warnInfoCache
     * @作者: 王嵊俊
     * @创建日期: 2016年6月17日 下午3:49:32
     */
    private void initCacheWarnInfo()
    {
        ServerCache.getWarnInfoCache().clear();
        Map<String, UserInfo> userCache = ServerCache.getuserInfoCache();
        
        for (UserInfo userInfo : userCache.values())
        {
            for (Map<Integer, WarnInfo> warnMap : userInfo.getWarnInfoMap().values())
            {
                for (WarnInfo warnInfo : warnMap.values())
                {
                    if ( warnInfo.isDelete() )
                    {
                        userInfo.removeWarnInfo(warnInfo);
                        continue;
                    }
                    warnInfo.setValidWarnCount(warnInfo.getWarnCount());
                    ServerCache.cacheWarnInfo(warnInfo);
                }
            }
        }
    }
    
    /**
     * @描述: 初始化数据库提醒有效次数
     * @作者: 王嵊俊
     * @创建日期: 2016年6月17日 下午3:49:32
     */
    private void initDBWarnInfo()
    {
        for (String tableName : ServerCache.getWarnTableNameCache().values())
        {
            ((NStockWarnService) BeanFactory.getBean(Key.NWarnService)).resetWarnCount(tableName);
        }
    }
    
}
