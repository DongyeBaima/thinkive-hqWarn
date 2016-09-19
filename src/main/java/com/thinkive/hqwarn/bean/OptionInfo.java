package com.thinkive.hqwarn.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OptionInfo
{
    private String                 stockCode;
                                   
    private Map<Integer, WarnInfo> warnInfoMap;
                                   
    public OptionInfo(String stockCode)
    {
        this.stockCode = stockCode;
        warnInfoMap = new HashMap<>();
    }
    
    public String getStockCode()
    {
        return stockCode;
    }
    
    public void addWarnInfo(WarnInfo warnInfo)
    {
        if ( warnInfo.isValid() )
        {
            warnInfoMap.put(warnInfo.getWarnType(), warnInfo);
        }
    }
    
    public void deleteWarnInfo(WarnInfo warnInfo)
    {
        deleteWarnInfo(warnInfo.getWarnType());
    }
    
    public void deleteWarnInfo(int warnType)
    {
        warnInfoMap.remove(warnType);
    }
    
    public WarnInfo getWarnInfo(int warnType)
    {
        return warnInfoMap.get(warnType);
    }
    
    public Set<Integer> getWarnTypeSet()
    {
        return warnInfoMap.keySet();
    }
    
    public void setStockCode(String stockCode)
    {
        this.stockCode = stockCode;
    }
    
}
