package com.thinkive.web.bean;

/**
 * 描述:
 * 版权:	 Copyright (c) 2006-2012
 * 公司:	 思迪科技
 * 作者:	 易庆锋
 * 版本:	 2.0
 * 创建日期: 2012-10-29
 * 创建时间: 15:30:10
 */
public interface Response
{
    
    public boolean write(byte[] data);
    
    public boolean write(Object data);
    
    public void setErrorNo(int errorNo);
    
    public int getErrorNo();
    
    public byte[] getData();
    
    public Object getResult();
    
    public void clear();
    
    public void setErrorInfo(String errorInfo);
    
    public String getErrorInfo();
    
    int getFuncNo();
    
}
