package com.thinkive.hqwarn.bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @����: �û���Ϣ
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @��������: 2016��5��4�� ����10:38:11
 */
public class UserInfo
{
    private String                              userID;     // �û�ID
                                                
    private Integer                             zoneID;     // ����ID
                                                
    private Integer                             tableID;    // �ֱ�ID
                                                
    private String                              token;
                                                
    private String                              mobileId;
                                                
    private Integer                             osId;
                                                
    private Map<String, Map<Integer, WarnInfo>> warnInfoMap;
                                                
    public UserInfo(String userID, Integer tableID, Integer zoneID)
    {
        this.userID = userID;
        this.tableID = tableID;
        this.zoneID = zoneID;
        
        warnInfoMap = new ConcurrentHashMap<>();
    }
    
    public String getUserID()
    {
        return userID;
    }
    
    public Integer getZoneID()
    {
        return zoneID;
    }
    
    public Integer getTableID()
    {
        return tableID;
    }
    
    public void addWarnInfo(WarnInfo warnInfo)
    {
        String stockCode = warnInfo.getStockCode();
        Integer warnType = warnInfo.getWarnType();
        
        Map<Integer, WarnInfo> map = warnInfoMap.get(stockCode);
        if ( map == null )
        {
            map = new ConcurrentHashMap<>();
            warnInfoMap.put(stockCode, map);
        }
        
        WarnInfo oldWarnInfo = map.get(warnType);
        
        if ( oldWarnInfo != null )
        {
            oldWarnInfo.setDelete(true);
        }
        
        map.put(warnType, warnInfo);
    }
    
    public WarnInfo getWarnInfo(String stockCode, Integer warnType)
    {
        Map<Integer, WarnInfo> map = warnInfoMap.get(stockCode);
        if ( map == null )
        {
            return null;
        }
        
        return map.get(warnType);
    }
    
    public Map<Integer, WarnInfo> getWarnInfo(String stockCode)
    {
        return warnInfoMap.get(stockCode);
    }
    
    public Map<String, Map<Integer, WarnInfo>> getWarnInfoMap()
    {
        return warnInfoMap;
    }
    
    public boolean removeWarnInfo(WarnInfo warnInfo)
    {
        String stockCode = warnInfo.getStockCode();
        Integer warnType = warnInfo.getWarnType();
        WarnInfo warn = getWarnInfo(stockCode, warnType);
        if ( warn == null )
        {
            return true;
        }
        if ( warn == warnInfo )
        {
            removeWarnInfo(stockCode, warnType);
            return true;
        }
        return false;
    }
    
    public boolean removeWarnInfo(String stockCode, Integer warnType)
    {
        WarnInfo warnInfo = getWarnInfo(stockCode, warnType);
        if ( warnInfo != null )
        {
            warnInfo.setDelete(true);
            warnInfoMap.get(stockCode).remove(warnType);
            return true;
        }
        return false;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public String getMobileId()
    {
        return mobileId;
    }
    
    public String getPushMark()
    {
        return getPushMark(osId);
    }
    
    public String getPushMark(int osId)
    {
        if ( osId == 1 && token != null && !token.trim().isEmpty() )
        {
            return token;
        }
        else if ( osId == 2 && mobileId != null && !mobileId.trim().isEmpty() )
        {
            return mobileId;
        }
        return userID;
    }
    
    public Integer getOsId()
    {
        return osId;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public void setMobileId(String mobileId)
    {
        this.mobileId = mobileId;
    }
    
    public void setOsId(Integer osId)
    {
        this.osId = osId;
    }
    
    @Override
    public String toString()
    {
        return "UserInfo [userID=" + userID + ", zoneID=" + zoneID + ", tableID=" + tableID + ", token=" + token
                + ", mobileId=" + mobileId + ", osId=" + osId + "]";
    }
    
}
