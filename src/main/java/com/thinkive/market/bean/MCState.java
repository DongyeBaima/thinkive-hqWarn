package com.thinkive.market.bean;

/**
 * @描述: 行情中心服务器状态对象
 * @版权: Copyright (c) 2012
 * @公司: 思迪科技
 * @作者: 岳知之
 * @版本: 1.0
 * @创建日期: 2012-3-29
 * @创建时间: 下午2:25:32
 */
public class MCState
{
    private String  initDate;    // 网关沪深初始化日期
                    
    private String  curHqDate;
                    
    private boolean isNeedUpdate;
                    
    private long    dbfTime;     // dbf文件的时间
                    
    private long    hkdbfTime;   // 港股通行情元文件的时间 毫秒数 xiongpan 2014-11-20
                    
    private String  hkInitDate;  // 网关港股初始化日期 王嵊俊 2014-12-31
                    
    private String  hkCurHqDate;
                    
    public long getDbfTime()
    {
        return dbfTime;
    }
    
    public void setDbfTime(long dbfTime)
    {
        this.dbfTime = dbfTime;
    }
    
    public String getInitDate()
    {
        return initDate;
    }
    
    public void setInitDate(String initDate)
    {
        this.initDate = initDate;
    }
    
    public String getCurHqDate()
    {
        return curHqDate;
    }
    
    public void setCurHqDate(String curHqDate)
    {
        this.curHqDate = curHqDate;
    }
    
    public boolean isNeedUpdate()
    {
        return isNeedUpdate;
    }
    
    public void setNeedUpdate(boolean isNeedUpdate)
    {
        this.isNeedUpdate = isNeedUpdate;
    }
    
    public long getHkdbfTime()
    {
        return hkdbfTime;
    }
    
    public void setHkdbfTime(long hkdbfTime)
    {
        this.hkdbfTime = hkdbfTime;
    }
    
    public String getHkInitDate()
    {
        return hkInitDate;
    }
    
    public void setHkInitDate(String hkInitDate)
    {
        this.hkInitDate = hkInitDate;
    }
    
    public String getHkCurHqDate()
    {
        return hkCurHqDate;
    }
    
    public void setHkCurHqDate(String hkCurHqDate)
    {
        this.hkCurHqDate = hkCurHqDate;
    }
    
    @Override
    public String toString()
    {
        return "MCState [initDate=" + initDate + ", curHqDate=" + curHqDate + ", isNeedUpdate=" + isNeedUpdate
                + ", dbfTime=" + dbfTime + ", hkdbfTime=" + hkdbfTime + ", hkInitDate=" + hkInitDate + ", hkCurHqDate="
                + hkCurHqDate + "]";
    }
    
}
