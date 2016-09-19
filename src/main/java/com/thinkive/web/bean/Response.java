package com.thinkive.web.bean;

/**
 * ����:
 * ��Ȩ:	 Copyright (c) 2006-2012
 * ��˾:	 ˼�ϿƼ�
 * ����:	 �����
 * �汾:	 2.0
 * ��������: 2012-10-29
 * ����ʱ��: 15:30:10
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
