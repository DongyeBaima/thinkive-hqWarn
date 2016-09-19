package com.thinkive.hqwarn.bean;

import java.util.concurrent.atomic.AtomicInteger;

import com.thinkive.hqwarn.util.WarnCacheHelper;

/**
 * @����: ������Ϣ
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��6�� ����11:23:30
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
                          
    private Integer       warnType;       // ��������
                          
    private Float         warnValue;      // ����ֵ
                          
    private Integer       warnCount;      // ���Ѵ���
                          
    private AtomicInteger validWarnCount; // �������Ѵ���
                          
    private boolean       isDelete;       // ɾ�����
                          
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
