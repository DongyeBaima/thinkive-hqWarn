package com.thinkive.warn.service.impl;

import com.thinkive.base.jdbc.DataRow;
import com.thinkive.base.jdbc.JdbcTemplate;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IServerStateService;

public class ServerStateService implements IServerStateService
{
    private final String       tableName = "t_serverstate";
                                         
    private final JdbcTemplate template  = BeanFactory.getBean(Key.DBTemplate);
                                         
    @Override
    public void heartbeat(ServerState serverState)
    {
        // TODO Auto-generated method stub
        String serverId = serverState.getServerId();
        String state = serverState.getState().name();
        String connServer = serverState.getConnServer();
        
        String sql = "update " + tableName
                + " set server_state = ?,  conn_server = ? , talk_time = unix_timestamp() , modified_date = now() where server_id = ?";
                
        template.update(sql, new Object[] { state, connServer, serverId });
    }
    
    @Override
    public void register(ServerState serverState)
    {
        String serverId = serverState.getServerId();
        
        if ( query(serverId) == null )
        {
            insert(serverState);
        }
        else
        {
            String state = serverState.getState().name();
            String connServer = serverState.getConnServer();
            int priority = serverState.getPriority();
            int zoneId = serverState.getZoneId();
            
            String sql = "update " + tableName
                    + " set server_state = ?, server_priority = ? , conn_server = ? ,zone_id = ?, talk_time = unix_timestamp() , modified_date = now() where server_id = ?";
                    
            template.update(sql, new Object[] { state, priority, connServer, zoneId, serverId });
        }
    }
    
    @Override
    public DataRow query(String serverId)
    {
        String sql = "select server_id,zone_id,server_state,conn_server,talk_time from " + tableName
                + " where server_id = ?";
                
        return template.queryMap(sql, new Object[] { serverId });
    }
    
    @Override
    public void insert(ServerState serverState)
    {
        String serverId = serverState.getServerId();
        int zoneId = serverState.getZoneId();
        String state = serverState.getState().name();
        int priority = serverState.getPriority();
        
        String sql = "insert into " + tableName
                + " (server_id,zone_id,server_priority,server_state,talk_time,create_date,modified_date) values ( ? ,?, ? , ? ,unix_timestamp(),now(),now())";
                
        template.update(sql, new Object[] { serverId, zoneId, priority, state });
    }
    
    @Override
    public void impeach(int timeout)
    {
        // TODO Auto-generated method stub
        String sql = "update " + tableName
                + " set server_state = ? , modified_date = now() where (unix_timestamp()-talk_time) > ? and server_state != ?";
                
        template.update(sql, new Object[] { State.excption.name(), timeout, State.excption.name() });
    }
    
    @Override
    public String selfChecking(ServerState serverState)
    {
        String serverId = serverState.getServerId();
        
        String sql = "select server_state from " + tableName + " where server_id = ?";
        
        DataRow data = template.queryMap(sql, new Object[] { serverId });
        
        return data.getString("server_state");
    }
    
    @Override
    public void vote(ServerState serverState)
    {
        int zoneId = serverState.getZoneId();
        String myServerId = serverState.getServerId();
        String myState = serverState.getState().name();
        String selectSql = "select server_id from " + tableName
                + " where zone_id = ? and (server_state = ? or server_state = ?) order by server_priority desc, server_id";
                
        DataRow data = template.queryMap(selectSql,
                new Object[] { zoneId, State.working.name(), State.hotspare.name() });
                
        if ( data == null )
        {
            return;
        }
        
        String serverId = data.getString("server_id");
        
        if ( serverId.equals(myServerId) )
        {
            if ( myState.equals(State.hotspare.name()) )
            {
                serverState.setState(State.working);
            }
        }
        else
        {
            if ( myState.equals(State.working.name()) )
            {
                serverState.setState(State.hotspare);
            }
        }
    }
    
}
