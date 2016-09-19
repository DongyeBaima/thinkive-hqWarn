package com.thinkive.hq.bean;

/**
 * @����: ʵʱ����
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��10�� ����5:27:38
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
