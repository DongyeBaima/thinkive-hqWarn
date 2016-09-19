package com.thinkive.hqwarn.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinkive.base.util.MyLogger;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.hqwarn.util.WarnCacheHelper;
import com.thinkive.timerengine.Task;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IDBService;

/**
 * @����: ����У��
 * @��Ȩ: Copyright (c) 2016
 * @��˾: Thinkive
 * @����: ���ӿ�
 * @��������: 2016��5��13�� ����4:41:51
 */
public class CacheCheckTask implements Task
{
    private static final Log     logger        = LogFactory.getLog("Server");
                                               
    private final List<WarnInfo> moreList      = new ArrayList<>();          // ���˵�����
                                               
    private final List<WarnInfo> missList      = new ArrayList<>();          // ���˵�����
                                               
    private final List<WarnInfo> differentList = new ArrayList<>();          // ��һ��������
                                               
    @Override
    public void execute()
    {
        logger.warn("	--	@����У������	--	��ʼ����");
        
        Map<String, UserInfo> checkUserInfoCache = new HashMap<>();
        
        cacheDataFromDB(checkUserInfoCache);
        
        serverCacheFilter();
        
        compareCache(checkUserInfoCache);
        
        logCompareInfo();
        
        logger.warn("	--	@����У������	--	�������");
        
    }
    
    private void cacheDataFromDB(Map<String, UserInfo> checkUserInfoCache)
    {
        IDBService<UserInfo> service = BeanFactory.getBean(Key.NUserService);
        List<UserInfo> userInfoList = service.query(new UserInfo(null, null, ServerConfig.SERVER_ZONEID));
        for (UserInfo row : userInfoList)
        {
            WarnCacheHelper.cacheUserInfo(row, checkUserInfoCache);
        }
    }
    
    /**
     * @����: ����ϵͳ��������Ч����
     * @����: ���ӿ�
     * @��������: 2016��5��13�� ����5:57:59
     */
    private void serverCacheFilter()
    {
        for (UserInfo user : ServerCache.getuserInfoCache().values())
        {
            for (Map<Integer, WarnInfo> warnMap : user.getWarnInfoMap().values())
            {
                for (WarnInfo warnInfo : warnMap.values())
                {
                    if ( warnInfo.isDelete() )
                    {
                        user.removeWarnInfo(warnInfo);
                    }
                }
            }
        }
    }
    
    private void compareCache(Map<String, UserInfo> checkUserInfoCache)
    {
        String userID = null;
        String stockCode = null;
        int warnType = 0;
        
        WarnInfo checkWarn = null;
        
        for (UserInfo user : ServerCache.getuserInfoCache().values())
        {
            for (Map<Integer, WarnInfo> warnMap : user.getWarnInfoMap().values())
            {
                for (WarnInfo warnInfo : warnMap.values())
                {
                    userID = warnInfo.getUserID();
                    stockCode = warnInfo.getStockCode();
                    warnType = warnInfo.getWarnType();
                    
                    checkWarn = getWarnInfo(checkUserInfoCache, userID, stockCode, warnType);
                    
                    if ( checkWarn == null )
                    {
                        moreList.add(warnInfo);
                    }
                    else
                    {
                        if ( compareWarnInfo(warnInfo, checkWarn) )
                        {
                            removeWarnInfo(checkUserInfoCache, userID, stockCode, warnType);
                        }
                        else
                        {
                            differentList.add(warnInfo);
                            differentList.add(checkWarn);
                        }
                    }
                }
            }
        }
        
        for (UserInfo user : checkUserInfoCache.values())
        {
            for (Map<Integer, WarnInfo> warnMap : user.getWarnInfoMap().values())
            {
                for (WarnInfo warnInfo : warnMap.values())
                {
                    missList.add(warnInfo);
                }
            }
        }
    }
    
    private boolean compareWarnInfo(WarnInfo a, WarnInfo b)
    {
        if ( !a.getUserID().equals(b.getUserID()) )
        {
            return false;
        }
        
        if ( !a.getStockCode().equals(b.getStockCode()) )
        {
            return false;
        }
        
        if ( a.getWarnType() != b.getWarnType() )
        {
            return false;
        }
        
        if ( a.getWarnValue() != b.getWarnValue() )
        {
            return false;
        }
        
        if ( a.getWarnCount() != b.getWarnCount() )
        {
            return false;
        }
        
        return true;
    }
    
    private WarnInfo getWarnInfo(Map<String, UserInfo> userInfoCache, String userID, String stockCode, int warnType)
    {
        
        UserInfo user = userInfoCache.get(userID);
        
        if ( user == null )
        {
            return null;
        }
        
        return user.getWarnInfo(stockCode, warnType);
    }
    
    private void removeWarnInfo(Map<String, UserInfo> userInfoCache, String userID, String stockCode, int warnType)
    {
        UserInfo user = userInfoCache.get(userID);
        if ( user != null )
        {
            user.removeWarnInfo(stockCode, warnType);
        }
    }
    
    private void logCompareInfo()
    {
        
        String lessName = "MissingInfo";
        String differentName = "DifferentInfo";
        String moreName = "MoreInfo";
        
        MyLogger lessLog = MyLogger.getLogger(lessName);
        MyLogger moreLog = MyLogger.getLogger(moreName);
        MyLogger difLog = MyLogger.getLogger(differentName);
        
        lessLog.clear();
        moreLog.clear();
        difLog.clear();
        
        for (WarnInfo warnInfo : missList)
        {
            lessLog.write(warnInfo);
        }
        
        for (WarnInfo warnInfo : moreList)
        {
            moreLog.write(warnInfo);
        }
        
        for (int i = 0, len = differentList.size(); i < len; i = i + 2)
        {
            difLog.write(differentList.get(i));
            difLog.write(differentList.get(i + 1));
            difLog.write("");
        }
        
        missList.clear();
        moreList.clear();
        differentList.clear();
        
    }
    
    public static void main(String[] args)
    {
        new CacheCheckTask().execute();
    }
    
}
