package com.thinkive.hqwarn.netty.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Message
{
    private int                 funcNo;
                                
    private int                 version;
                                
    private Map<String, Object> requestParamMap = new HashMap<>();
                                                
    private byte[]              data;
                                
    public byte[] getData()
    {
        return data;
    }
    
    public void setData(byte[] data)
    {
        this.data = data;
    }
    
    public int getFuncNo()
    {
        return funcNo;
    }
    
    public Set<String> getRequestParamKeySet()
    {
        return requestParamMap.keySet();
    }
    
    public Object getParam(String key)
    {
        return requestParamMap.get(key);
    }
    
    public void setFuncNo(int funcNo)
    {
        this.funcNo = funcNo;
    }
    
    public void setParam(String key, Object value)
    {
        requestParamMap.put(key, value);
    }
    
    public int getVersion()
    {
        return version;
    }
    
    public void setVersion(int version)
    {
        this.version = version;
    }
}
