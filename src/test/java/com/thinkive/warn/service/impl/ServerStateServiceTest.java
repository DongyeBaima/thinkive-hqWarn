package com.thinkive.warn.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.thinkive.base.jdbc.JdbcTemplate;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IServerStateService;

public class ServerStateServiceTest
{
    IServerStateService service;
    
    @Before
    public final void before()
    {
        BeanFactory.init();
        service = BeanFactory.getBean(Key.ServerStateService);
    }
    
    @Test
    public final void testInsert()
    {
        String sql = "delete from t_serverstate ";
        
        JdbcTemplate template = new JdbcTemplate();
        template.update(sql);
        
        ServerState serverState = new ServerState("warn_1", 2, 8);
        serverState.setState(State.excption);
        service.insert(serverState);
        
        serverState = new ServerState("warn_3", 2, 7);
        serverState.setState(State.initializing);
        service.insert(serverState);
        
        serverState = new ServerState("warn_4", 2, 6);
        serverState.setState(State.working);
        service.insert(serverState);
        
        serverState = new ServerState("warn_5", 2, 5);
        serverState.setState(State.hotspare);
        service.insert(serverState);
        
        serverState = new ServerState("warn_6", 2, 4);
        serverState.setState(State.hotspare);
        service.insert(serverState);
        
        serverState = new ServerState("warn_7", 2, 3);
        serverState.setState(State.hotspare);
        service.insert(serverState);
        
        serverState = new ServerState("warn_8", 2, 2);
        serverState.setState(State.hotspare);
        service.insert(serverState);
        
        service.vote(serverState);
    }
    
    @Ignore
    @Test
    public final void testHeartbeat()
    {
    
    }
    
    @Ignore
    @Test
    public final void testCheckOtherServerIsWorking()
    {
        service.impeach(2);
    }
    
}
