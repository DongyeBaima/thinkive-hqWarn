package com.thinkive.hqwarn.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import com.thinkive.base.util.MyLog;
import com.thinkive.warn.entity.State;

/**
 * @描述: 服务器状态
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月4日 下午2:55:20
 */
public class ServerState
{
    private String            serverId;
                              
    private State             state;
                              
    private int               zoneId;                      // 分区ID， 提醒服务器默认为-1
                              
    private int               priority;                    // 成为主键的优先级
                              
    private Map<String, Long> connServer = new HashMap<>();
                                         
    public ServerState(String serverId, int zoneId, int priority)
    {
        // TODO Auto-generated constructor stub
        state = State.initializing;
        this.serverId = serverId;
        this.zoneId = zoneId;
        this.priority = priority;
    }
    
    public String getServerId()
    {
        return serverId;
    }
    
    public int getZoneId()
    {
        return zoneId;
    }
    
    public void setServerId(String serverId)
    {
        this.serverId = serverId;
    }
    
    public void setZoneId(int zoneId)
    {
        this.zoneId = zoneId;
    }
    
    public String getConnServer()
    {
        return JSON.toString(connServer);
    }
    
    public void serverTalk(String serverId)
    {
        connServer.put(serverId, System.currentTimeMillis());
    }
    
    public void removeConnServer(String serverId)
    {
        connServer.remove(serverId);
    }
    
    public boolean hasServerConn(long timeout)
    {
        // -- -- -- 清空过期连接
        long currentTime = System.currentTimeMillis();
        Iterator<String> iterator = connServer.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            long talktime = connServer.get(key);
            
            if ( (currentTime - talktime) > timeout )
            {
                iterator.remove();
                connServer.remove(key);
            }
        }
        
        return !connServer.isEmpty();
    }
    
    public int getPriority()
    {
        return priority;
    }
    
    public void setPriority(int priority)
    {
        this.priority = priority;
    }
    
    public State getState()
    {
        return state;
    }
    
    public synchronized void setState(State state)
    {
        if ( !this.state.equals(state) )
        {
            MyLog.serverLog.warn("  --  --  系统状态改变  : " + this.state + " -> " + state);
            
            this.state = state;
        }
    }
    
    public boolean isInit()
    {
        return !state.equals(State.initializing);
    }
}
