package com.thinkive.hqwarn.util;

import java.util.List;
import java.util.Map;

import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IDBService;

/**
 * @描述: 用户相关帮助类
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月4日 下午4:55:11
 */
public class WarnCacheHelper
{
    private static final IDBService<UserInfo> userService = BeanFactory.getBean(Key.NUserService);
    
    /**
     * @描述: 获取存储用户自选股信息的表名
     * @作者: 王嵊俊
     * @创建日期: 2016年5月4日 下午5:03:59
     * @param user
     * @return
     */
    public static final String getOptionTabelName(UserInfo user)
    {
        return ServerCache.getOptionTableName(user.getTableID());
    }
    
    public static final String getOptionTabelName(String userId)
    {
        UserInfo user = ServerCache.getUser(userId);
        return ServerCache.getOptionTableName(user.getTableID());
    }
    
    /**
     * @描述: 获取存储用户提醒信息的表名
     * @作者: 王嵊俊
     * @创建日期: 2016年5月4日 下午5:03:59
     * @param user
     * @return
     */
    public static final String getWarnTabelName(UserInfo user)
    {
        return ServerCache.getWarnTableName(user.getTableID());
    }
    
    /**
     * @描述: 获取存储用户提醒信息的表名
     * @作者: 王嵊俊
     * @创建日期: 2016年5月4日 下午5:03:59
     * @param user
     * @return
     */
    public static final String getWarnTabelName(String userID)
    {
        UserInfo user = getUserInfo(userID);
        if ( user == null )
        {
            return null;
        }
        return ServerCache.getWarnTableName(user.getTableID());
    }
    
    /**
     * @描述: 查找用户信息
     * @作者: 王嵊俊
     * @创建日期: 2016年5月4日 下午7:55:31
     * @param userID
     * @return
     */
    public static final UserInfo getUserInfo(String userID)
    {
        UserInfo user = ServerCache.getUser(userID);
        
        if ( user == null )
        {
            user = userService.query(new UserInfo(userID, null, null)).get(0);
            
            if ( user != null )
            {
                cacheUserInfo(user);
            }
        }
        
        return user;
    }
    
    /**
     * @描述: 根据数据库数据 生成 用户信息对象
     * @作者: 王嵊俊
     * @创建日期: 2016年5月9日 下午4:50:04
     * @param data
     * @return
     */
    public static final UserInfo cacheUserInfo(UserInfo user)
    {
        return cacheUserInfo(user, ServerCache.getuserInfoCache());
    }
    
    /**
     * @描述: 根据数据库数据 生成 用户信息对象
     * @作者: 王嵊俊
     * @创建日期: 2016年5月9日 下午4:50:04
     * @param data
     * @return
     */
    public static final UserInfo cacheUserInfo(UserInfo user, Map<String, UserInfo> dataCache)
    {
        dataCache.put(user.getUserID(), user);
        cacheWarnInfo(user);
        return user;
    }
    
    /**
     * @描述: 缓存提醒信息
     * @作者: 王嵊俊
     * @创建日期: 2016年6月13日 下午3:00:53
     * @param user
     * @param warnInfo
     */
    public static final void cacheWarnInfo(UserInfo user, WarnInfo warnInfo)
    {
        user.addWarnInfo(warnInfo);
        ServerCache.cacheWarnInfo(warnInfo);
    }
    
    /**
     * @描述: 缓存提醒信息
     * @作者: 王嵊俊
     * @创建日期: 2016年5月9日 下午5:04:06
     */
    public static final void cacheWarnInfo(UserInfo user)
    {
        IDBService<WarnInfo> stockWarnService = BeanFactory.getBean(Key.NWarnService);
        List<WarnInfo> dataList = stockWarnService.query(new WarnInfo(user.getUserID(), null, null));
        
        for (WarnInfo data : dataList)
        {
            ServerCache.cacheWarnInfo(data);
            user.addWarnInfo(data);
        }
    }
    
    /**
     * @描述: 按顺序插入warnInfo
     * @作者: 王嵊俊
     * @创建日期: 2016年5月10日 上午10:13:57
     * @param warnInfo
     * @param list
     */
    public static void sortInsertWarnInfo(WarnInfo warnInfo, List<WarnInfo> list)
    {
        int index = findSortIndex(warnInfo, list);
        
        list.add(index, warnInfo);
    }
    
    /**
     * @描述: 用二分法 找到warnInfo的排序位置
     * @作者: 王嵊俊
     * @创建日期: 2016年5月10日 上午10:37:44
     * @param warnInfo
     * @param list
     * @param start
     * @param end
     * @return
     */
    public static int findSortIndex(WarnInfo warnInfo, List<WarnInfo> list)
    {
        return findSortIndex(warnInfo.getWarnValue(), list);
    }
    
    public static int findSortIndex(double warnValue, List<WarnInfo> list)
    {
        synchronized (list)
        {
            return findSortIndexUnsafe(warnValue, list);
        }
    }
    
    public static int findSortIndexUnsafe(double warnValue, List<WarnInfo> list)
    {
        return binarySearch(warnValue, list, 0, list.size() - 1);
    }
    
    /**
     * @描述: 用二分法 找到warnInfo的排序位置
     * @作者: 王嵊俊
     * @创建日期: 2016年5月10日 上午10:37:44
     * @param warnInfo
     * @param list
     * @param start
     * @param end
     * @return
     */
    public static int findSortIndex(double warnValue, List<WarnInfo> list, int start, int end)
    {
        
        if ( start > end )
        {
            return start;
        }
        
        int mid = (start + end) / 2;
        
        switch (warnInfoCompare(warnValue, list.get(mid)))
        {
            case 0:
                return mid;
            case 1:
                return findSortIndex(warnValue, list, mid + 1, end);
            case -1:
                return findSortIndex(warnValue, list, start, mid - 1);
        }
        
        return 0;
    }
    
    /**
     * @描述: 用二分法 找到warnInfo的排序位置
     * @作者: 王嵊俊
     * @创建日期: 2016年5月10日 上午10:37:44
     * @param warnInfo
     * @param list
     * @param start
     * @param end
     * @return
     */
    public static int binarySearch(double warnValue, List<WarnInfo> list, int start, int end)
    {
        while (start <= end)
        {
            if ( start == end )
            {
                return start;
            }
            
            int mid = (start + end) >> 1;
            
            float midValue = list.get(mid).getWarnValue();
            
            if ( warnValue > midValue )
            {
                start = mid + 1;
            }
            else if ( warnValue < midValue )
            {
                end = mid - 1;
            }
            else
            {
                return mid;
            }
        }
        
        return 0;
    }
    
    public static void destroydWarn(WarnInfo warnInfo)
    {
        if ( warnInfo == null )
        {
            return;
        }
        
        if ( !warnInfo.isValid() )
        {
            String userID = warnInfo.getUserID();
            String stockCode = warnInfo.getStockCode();
            int warnType = warnInfo.getWarnType();
            UserInfo user = ServerCache.getUser(userID);
            
            List<WarnInfo> list = ServerCache.getWarnInfoCache(stockCode, warnType);
            if ( list != null )
            {
                list.remove(warnInfo);
            }
            
            if ( warnInfo.isDelete() )
            {
                user.removeWarnInfo(warnInfo);
            }
        }
    }
    
    /**
     * @描述: 比较 WarnValue  的大小，用以排序
     * @作者: 王嵊俊
     * @创建日期: 2016年5月10日 上午10:38:11
     * @param o1
     * @param o2
     * @return
     */
    public static int warnInfoCompare(double warnValue1, WarnInfo o2)
    {
        // TODO Auto-generated method stub
        
        double warnValue2 = o2.getWarnValue();
        
        if ( warnValue1 > warnValue2 )
        {
            return 1;
        }
        
        if ( warnValue1 < warnValue2 )
        {
            return -1;
        }
        
        return 0;
    }
    
}
