package com.thinkive.hqwarn.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 连接地址类
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年6月24日 上午10:44:19
 */
public class NetConnAddress<T>
{
    private List<T> addressList;
                    
    private T       connAddress;
                    
    private int     connIndex;
                    
    public NetConnAddress()
    {
        connIndex = -1;
        addressList = new ArrayList<>();
    }
    
    public NetConnAddress(T[] addressArray)
    {
        connIndex = -1;
        addressList = new ArrayList<>();
        
        setAddressList(addressArray);
    }
    
    public void setConnAddress(int index)
    {
        if ( index < addressList.size() )
        {
            connIndex = index;
            connAddress = addressList.get(connIndex);
        }
    }
    
    public T changeAddress()
    {
        if ( addressList == null || addressList.isEmpty() )
        {
            return null;
        }
        
        connIndex++;
        
        if ( connIndex >= addressList.size() )
        {
            connIndex = 0;
        }
        
        connAddress = addressList.get(connIndex);
        
        return connAddress;
        
    }
    
    public List<T> getAddressList()
    {
        return addressList;
    }
    
    public T getConnAddress()
    {
        if ( connAddress == null )
        {
            return changeAddress();
        }
        
        return connAddress;
    }
    
    public int getConnIndex()
    {
        return connIndex;
    }
    
    public void setAddressList(List<T> addressList)
    {
        this.addressList = addressList;
    }
    
    public void setAddressList(T addressArray[])
    {
        if ( addressArray != null )
        {
            for (T t : addressArray)
            {
                addressList.add(t);
            }
            
            if ( !addressList.isEmpty() )
            {
                connIndex++;
                connAddress = addressList.get(connIndex);
            }
        }
    }
    
    public void setConnAddress(T connAddress)
    {
        int index = addressList.indexOf(connAddress);
        
        if ( index >= 0 )
        {
            connIndex = index;
        }
        else
        {
            addressList.add(connAddress);
            connIndex = addressList.size() - 1;
        }
        
        this.connAddress = connAddress;
    }
    
    public void setConnIndex(int connIndex)
    {
        this.connIndex = connIndex;
    }
    
    public int size()
    {
        return addressList.size();
    }
    
}
