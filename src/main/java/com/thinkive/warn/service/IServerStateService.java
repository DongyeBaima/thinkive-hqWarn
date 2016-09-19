package com.thinkive.warn.service;

import com.thinkive.base.jdbc.DataRow;
import com.thinkive.hqwarn.cache.ServerState;

public interface IServerStateService
{
    DataRow query(String serverId);
    
    /**
     * @����: ���
     * @����: ���ӿ�
     * @��������: 2016��8��12�� ����5:22:52
     * @param timeout
     */
    void impeach(int timeout);
    
    void heartbeat(ServerState serverState);
    
    void insert(ServerState serverState);
    
    void vote(ServerState serverState);
    
    String selfChecking(ServerState serverState);

    void register(ServerState serverState);
    
}
