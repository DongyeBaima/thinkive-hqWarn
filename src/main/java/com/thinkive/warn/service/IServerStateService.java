package com.thinkive.warn.service;

import com.thinkive.base.jdbc.DataRow;
import com.thinkive.hqwarn.cache.ServerState;

public interface IServerStateService
{
    DataRow query(String serverId);
    
    /**
     * @描述: 检举
     * @作者: 王嵊俊
     * @创建日期: 2016年8月12日 下午5:22:52
     * @param timeout
     */
    void impeach(int timeout);
    
    void heartbeat(ServerState serverState);
    
    void insert(ServerState serverState);
    
    void vote(ServerState serverState);
    
    String selfChecking(ServerState serverState);

    void register(ServerState serverState);
    
}
