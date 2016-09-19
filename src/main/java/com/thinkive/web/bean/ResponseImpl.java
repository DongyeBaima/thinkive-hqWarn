package com.thinkive.web.bean;

import com.thinkive.hqwarn.util.FuncHelper;

/**
 * @����: ������ͻ��˷�������
 * @��Ȩ: Copyright (c) 2012 
 * @��˾: ˼�ϿƼ� 
 * @����: ��֪֮
 * @�汾: 1.0 
 * @��������: 2012-3-14 
 * @����ʱ��: ����3:05:56
 */
public class ResponseImpl implements Response
{
    
    private byte[] data      = new byte[0];
                             
    private Object result    = null;
                             
    private int    errorNo   = 0;
                             
    private String errorInfo = "";
                             
    private int    funcNo    = 0;
                             
    @Override
    public boolean write(byte[] data)
    {
        // TODO Auto-generated method stub
        
        this.data = data;
        
        return true;
    }
    
    @Override
    public boolean write(Object data)
    {
        // TODO Auto-generated method stub
        this.result = data;
        
        return true;
    }
    
    @Override
    public void setErrorNo(int errorNo)
    {
        // TODO Auto-generated method stub
        
        if ( errorNo != FuncHelper.SUCCESS )
        {
            errorNo = -(funcNo * 100 + errorNo);
        }
        
        this.errorNo = errorNo;
    }
    
    @Override
    public int getErrorNo()
    {
        // TODO Auto-generated method stub
        return errorNo;
    }
    
    @Override
    public byte[] getData()
    {
        // TODO Auto-generated method stub
        return data;
    }
    
    @Override
    public Object getResult()
    {
        // TODO Auto-generated method stub
        return result;
    }
    
    @Override
    public void clear()
    {
        // TODO Auto-generated method stub
        data = new byte[0];
        result = null;
        errorInfo = "";
        errorNo = 0;
    }
    
    @Override
    public void setErrorInfo(String errorInfo)
    {
        // TODO Auto-generated method stub
        this.errorInfo = errorInfo;
    }
    
    @Override
    public String getErrorInfo()
    {
        // TODO Auto-generated method stub
        return errorInfo;
    }
    
    @Override
    public int getFuncNo()
    {
        return funcNo;
    }
    
    public void setFuncNo(int funcNo)
    {
        this.funcNo = funcNo;
    }
    
}
