package com.thinkive.hqwarn.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thinkive.base.jdbc.DataRow;
import com.thinkive.base.util.MyLog;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.hqwarn.util.WarnCacheHelper;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IDBService;

/**
 * @����: ������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��4�� ����6:05:26
 */
public class ServerCache
{
    private static final Map<String, UserInfo>                     userInfoCache      = new HashMap<>(); // �û���Ϣ����
                                                                                      
    private static final Map<Integer, String>                      optionTableNameMap = new HashMap<>(); // ��ѡ�ɱ�������
                                                                                      
    private static final Map<Integer, String>                      warnTableNameMap   = new HashMap<>(); // ���ѱ�������
                                                                                      
    // --<��Ʊ���룬<�������ͣ�������ֵ�����warninfo����>>
    private static final Map<String, Map<Integer, List<WarnInfo>>> warnInfoCache      = new HashMap<>(); // ������Ϣ����
                                                                                      
    private static final Map<String, Stock>                        hqInfoCache        = new HashMap<>(); // �������ݻ���
                                                                                      
    public static final Map<String, Stock> getHQCache()
    {
        return hqInfoCache;
    }
    
    public static final boolean init()
    {
        IDBService<UserInfo> userService = BeanFactory.getBean(Key.NUserService);
        ServerState serverState = BeanFactory.getBean(Key.ServerState);
        State tempState = serverState.getState();
        try
        {
            List<UserInfo> userInfoList = userService.query(new UserInfo(null, null, ServerConfig.SERVER_ZONEID));
            serverState.setState(State.initializing);
            clearWarnCache();
            
            for (UserInfo user : userInfoList)
            {
                WarnCacheHelper.cacheUserInfo(user);
            }
            
            return true;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            
            MyLog.serverLog.warn("", e);
            return false;
        }
        finally
        {
            if ( serverState.getState().equals(State.initializing) )
            {
                serverState.setState(tempState);
            }
        }
        
    }
    
    public static final void cacheWarnInfo(List<DataRow> dataList)
    {
        for (DataRow data : dataList)
        {
            cacheWarnInfo(data);
        }
    }
    
    public static final void cacheWarnInfo(DataRow data)
    {
    }
    
    public static final void clearWarnCache()
    {
        warnInfoCache.clear();
        userInfoCache.clear();
    }
    
    public static final void clearHQCache()
    {
        hqInfoCache.clear();
    }
    
    public static final Set<String> getStockCacheKeySet()
    {
        return hqInfoCache.keySet();
    }
    
    public static final Stock getStock(String stockCode)
    {
        return hqInfoCache.get(stockCode);
    }
    
    public static final void cacheStock(Stock stock)
    {
        String stockCode = stock.getStockCode();
        
        hqInfoCache.put(stockCode, stock);
    }
    
    public static final Map<String, Map<Integer, List<WarnInfo>>> getWarnInfoCache()
    {
        return warnInfoCache;
    }
    
    public static final Map<Integer, List<WarnInfo>> getWarnInfoCache(String stockCode)
    {
        return warnInfoCache.get(stockCode);
    }
    
    public static final List<WarnInfo> getWarnInfoCache(String stockCode, int warnType)
    {
        Map<Integer, List<WarnInfo>> stockMap = getWarnInfoCache(stockCode);
        
        if ( stockMap == null )
        {
            return null;
        }
        
        return stockMap.get(warnType);
    }
    
    public static final void deleteWarnInfo(WarnInfo warnInfo)
    {
        warnInfo.setDelete(true);
    }
    
    /**
     * @����: ��warninfo ���浽 warnInfoCache
     * @����: ���ӿ�
     * @��������: 2016��6��17�� ����5:32:06
     * @param warnInfo
     */
    public static final void cacheWarnInfo(WarnInfo warnInfo)
    {
        String stockCode = warnInfo.getStockCode();
        int warnType = warnInfo.getWarnType();
        
        Map<Integer, List<WarnInfo>> map = warnInfoCache.get(stockCode);
        if ( map == null )
        {
            map = new HashMap<>();
            warnInfoCache.put(stockCode, map);
        }
        
        List<WarnInfo> list = map.get(warnType);
        if ( list == null )
        {
            list = Collections.synchronizedList(new ArrayList<WarnInfo>());
            map.put(warnType, list);
        }
        
        WarnCacheHelper.sortInsertWarnInfo(warnInfo, list);
    }
    
    public static Map<String, UserInfo> getuserInfoCache()
    {
        return userInfoCache;
    }
    
    public static UserInfo getUser(String userID)
    {
        return userInfoCache.get(userID);
    }
    
    public static void cacheUser(UserInfo user)
    {
        String userID = user.getUserID();
        userInfoCache.put(userID, user);
    }
    
    public static String getOptionTableName(int tableID)
    {
        String tableName = optionTableNameMap.get(tableID);
        
        if ( tableName == null )
        {
            if ( tableID < 10 )
            {
                tableName = ServerConfig.DB_TABLENAME_OPTION + "0" + tableID;
            }
            else
            {
                tableName = ServerConfig.DB_TABLENAME_OPTION + tableID;
            }
            
            optionTableNameMap.put(tableID, tableName);
        }
        
        return tableName;
    }
    
    public static String getWarnTableName(int tableID)
    {
        String tableName = warnTableNameMap.get(tableID);
        
        if ( tableName == null )
        {
            if ( tableID < 10 )
            {
                tableName = ServerConfig.DB_TABLENAME_WARN + "0" + tableID;
            }
            else
            {
                tableName = ServerConfig.DB_TABLENAME_WARN + tableID;
            }
            
            warnTableNameMap.put(tableID, tableName);
        }
        
        return tableName;
    }
    
    public static Map<Integer, String> getWarnTableNameCache()
    {
        return warnTableNameMap;
    }
    
}
