package com.thinkive.hqwarn.bean;

import com.thinkive.hq.bean.Stock;

/**
 * @描述: 推送信息类
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月12日 下午5:00:17
 */
public class WarnPushInfo
{
    private WarnInfo warnInfo;
                     
    private String   message;
                     
    private String   title;
                     
    private String   content;
                     
    private String   showTimeStr;
                     
    private Stock    stock;
                     
    public Stock getStock()
    {
        return stock;
    }
    
    public void setStock(Stock stock)
    {
        this.stock = stock;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public String getShowTimeStr()
    {
        return showTimeStr;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public void setShowTimeStr(String showTimeStr)
    {
        this.showTimeStr = showTimeStr;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public WarnInfo getWarnInfo()
    {
        return warnInfo;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public void setWarnInfo(WarnInfo warnInfo)
    {
        this.warnInfo = warnInfo;
    }
    
    @Override
    public String toString()
    {
        return "WarnPushInfo [warnInfo=" + warnInfo + ", message=" + message + ", title=" + title + ", content="
                + content + ", showTimeStr=" + showTimeStr + ", stock=" + stock + "]";
    }
    
}
