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
 * @����: ��ʼ��������Ч����
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��6��17�� ����3:48:05
 */
public class WarnInfoInitTask implements Task
{
    
    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        MyLog.serverLog.warn("	--	@������Ϣ��ʼ������	--	����ʼ");
        initCacheWarnInfo();
        initDBWarnInfo();
        MyLog.serverLog.warn("	--	@������Ϣ��ʼ������	--	�������");
    }
    
    /**
     * @����: ��ʼ������������Ч����,���»��� warnInfoCache
     * @����: ���ӿ�
     * @��������: 2016��6��17�� ����3:49:32
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
     * @����: ��ʼ�����ݿ�������Ч����
     * @����: ���ӿ�
     * @��������: 2016��6��17�� ����3:49:32
     */
    private void initDBWarnInfo()
    {
        for (String tableName : ServerCache.getWarnTableNameCache().values())
        {
            ((NStockWarnService) BeanFactory.getBean(Key.NWarnService)).resetWarnCount(tableName);
        }
    }
    
}
