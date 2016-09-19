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
 * @����: �û���ذ�����
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��4�� ����4:55:11
 */
public class WarnCacheHelper
{
    private static final IDBService<UserInfo> userService = BeanFactory.getBean(Key.NUserService);
    
    /**
     * @����: ��ȡ�洢�û���ѡ����Ϣ�ı���
     * @����: ���ӿ�
     * @��������: 2016��5��4�� ����5:03:59
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
     * @����: ��ȡ�洢�û�������Ϣ�ı���
     * @����: ���ӿ�
     * @��������: 2016��5��4�� ����5:03:59
     * @param user
     * @return
     */
    public static final String getWarnTabelName(UserInfo user)
    {
        return ServerCache.getWarnTableName(user.getTableID());
    }
    
    /**
     * @����: ��ȡ�洢�û�������Ϣ�ı���
     * @����: ���ӿ�
     * @��������: 2016��5��4�� ����5:03:59
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
     * @����: �����û���Ϣ
     * @����: ���ӿ�
     * @��������: 2016��5��4�� ����7:55:31
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
     * @����: �������ݿ����� ���� �û���Ϣ����
     * @����: ���ӿ�
     * @��������: 2016��5��9�� ����4:50:04
     * @param data
     * @return
     */
    public static final UserInfo cacheUserInfo(UserInfo user)
    {
        return cacheUserInfo(user, ServerCache.getuserInfoCache());
    }
    
    /**
     * @����: �������ݿ����� ���� �û���Ϣ����
     * @����: ���ӿ�
     * @��������: 2016��5��9�� ����4:50:04
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
     * @����: ����������Ϣ
     * @����: ���ӿ�
     * @��������: 2016��6��13�� ����3:00:53
     * @param user
     * @param warnInfo
     */
    public static final void cacheWarnInfo(UserInfo user, WarnInfo warnInfo)
    {
        user.addWarnInfo(warnInfo);
        ServerCache.cacheWarnInfo(warnInfo);
    }
    
    /**
     * @����: ����������Ϣ
     * @����: ���ӿ�
     * @��������: 2016��5��9�� ����5:04:06
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
     * @����: ��˳�����warnInfo
     * @����: ���ӿ�
     * @��������: 2016��5��10�� ����10:13:57
     * @param warnInfo
     * @param list
     */
    public static void sortInsertWarnInfo(WarnInfo warnInfo, List<WarnInfo> list)
    {
        int index = findSortIndex(warnInfo, list);
        
        list.add(index, warnInfo);
    }
    
    /**
     * @����: �ö��ַ� �ҵ�warnInfo������λ��
     * @����: ���ӿ�
     * @��������: 2016��5��10�� ����10:37:44
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
     * @����: �ö��ַ� �ҵ�warnInfo������λ��
     * @����: ���ӿ�
     * @��������: 2016��5��10�� ����10:37:44
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
     * @����: �ö��ַ� �ҵ�warnInfo������λ��
     * @����: ���ӿ�
     * @��������: 2016��5��10�� ����10:37:44
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
     * @����: �Ƚ� WarnValue  �Ĵ�С����������
     * @����: ���ӿ�
     * @��������: 2016��5��10�� ����10:38:11
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
