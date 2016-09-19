package com.thinkive.hqwarn.bean;

import java.util.concurrent.atomic.AtomicInteger;

import com.thinkive.hqwarn.util.WarnCacheHelper;

/**
 * @描述: 提醒信息
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月6日 上午11:23:30
 */
public class WarnInfo
{
    public WarnInfo()
    {
        validWarnCount = new AtomicInteger(0);
        this.isDelete = false;
    }
    
    public WarnInfo(String userId, String stockCode, Integer warnType)
    {
        this.userID = userId;
        this.stockCode = stockCode;
        if ( warnType != null )
        {
            this.warnType = warnType;
        }
    }
    
    public WarnInfo(String userID, String stockCode, Integer warnType, Float warnValue, Integer warnCount,
            Integer warnValidCount)
    {
        this.userID = userID;
        this.stockCode = stockCode;
        this.warnType = warnType;
        this.warnValue = warnValue;
        this.warnCount = warnCount;
        this.validWarnCount = new AtomicInteger(warnValidCount);
        this.isDelete = false;
    }
    
    private String        stockCode;
                          
    private String        userID;
                          
    private Integer       warnType;       // 提醒类型
                          
    private Float         warnValue;      // 提醒值
                          
    private Integer       warnCount;      // 提醒次数
                          
    private AtomicInteger validWarnCount; // 可用提醒次数
                          
    private boolean       isDelete;       // 删除标记
                          
    public Integer getWarnType()
    {
        return warnType;
    }
    
    public Float getWarnValue()
    {
        return warnValue;
    }
    
    public Integer getWarnCount()
    {
        return warnCount;
    }
    
    public void setWarnType(Integer warnType)
    {
        this.warnType = warnType;
    }
    
    public void setWarnValue(Float warnValue)
    {
        this.warnValue = warnValue;
    }
    
    public void setWarnCount(Integer warnCount)
    {
        this.warnCount = warnCount;
    }
    
    public boolean isDelete()
    {
        return isDelete;
    }
    
    public void setDelete(boolean isDelete)
    {
        if ( this.isDelete != isDelete )
        {
            this.isDelete = isDelete;
            if ( isDelete && userID != null )
            {
                WarnCacheHelper.destroydWarn(this);
            }
        }
    }
    
    public Integer getValidWarnCount()
    {
        return validWarnCount.get();
    }
    
    public void setValidWarnCount(Integer validWarnCount)
    {
        this.validWarnCount.set(validWarnCount);
    }
    
    public boolean isValid()
    {
        return validWarnCount.get() > 0 && !isDelete;
    }
    
    public String getStockCode()
    {
        return stockCode;
    }
    
    public String getUserID()
    {
        return userID;
    }
    
    public void setStockCode(String stockCode)
    {
        this.stockCode = stockCode;
    }
    
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
    
    public int incrementValidCount()
    {
        return validWarnCount.incrementAndGet();
    }
    
    public int decrementValidCount()
    {
        return validWarnCount.decrementAndGet();
    }
    
    @Override
    public String toString()
    {
        return "WarnInfo [stockCode=" + stockCode + ", userID=" + userID + ", warnType=" + warnType + ", warnValue="
                + warnValue + ", warnCount=" + warnCount + ", validWarnCount=" + validWarnCount + ", isDelete="
                + isDelete + "]";
    }
    
}
