package com.thinkive.hqwarn.bean;

import com.thinkive.hq.bean.Stock;

/**
 * @����: ������Ϣ��
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��12�� ����5:00:17
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
