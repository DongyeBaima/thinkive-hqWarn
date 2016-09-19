package com.thinkive.hq.bean;

/**
 * @描述: 实时数据
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月10日 下午5:27:38
 */
public class RealTimeData
{
    private long    time;
                    
    private float   data;
                    
    private boolean isValid;
                    
    public RealTimeData()
    {
        isValid = true;
    }
    
    public RealTimeData(long time, float data)
    {
        super();
        this.time = time;
        this.data = data;
    }
    
    public boolean isValid()
    {
        return isValid;
    }
    
    public void setValid(boolean isValid)
    {
        this.isValid = isValid;
    }
    
    public long getTime()
    {
        return time;
    }
    
    public float getData()
    {
        return data;
    }
    
    public void setTime(long time)
    {
        this.time = time;
    }
    
    public void setData(float data)
    {
        this.data = data;
    }
    
}
