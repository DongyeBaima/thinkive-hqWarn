package com.thinkive.hqwarn.heartbeat.impl;

import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.hqwarn.heartbeat.AbstractHeartbeat;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IServerStateService;

@Deprecated
public class DBHeartbeat extends AbstractHeartbeat
{
    private final IServerStateService service              = BeanFactory.getBean(Key.ServerStateService);
                                                           
    private final ServerState         serverState          = BeanFactory.getBean(Key.ServerState);
                                                           
    private final int                 retryCount           = 3;                                          // 心跳失败重试次数
                                                           
    private final int                 timeout_db           = 2;                                          //服务器与数据库心跳超时时间，单位：S                                              
                                                           
    private final int                 timeout_option       = 3000;                                       //与自选股服务器心跳超时时间 ， 单位ms；
                                                           
    private final int                 disConnPrintLogCount = 3;                                          // 与数据库失去连接3次内打印日志
                                                           
    private int                       disconnCount         = 0;                                          //与数据库失去连接次数
                                                           
    public DBHeartbeat(long idleTime)
    {
        // TODO Auto-generated constructor stub
        super(idleTime);
    }
    
    /* (non-Javadoc)
     * @see com.thinkive.hqwarn.heartbeat.IHeartBeat#heartbeat()
     */
    @Override
    public void heartbeat()
    {
        // TODO Auto-generated method stub
        
        for (int i = 0; i < retryCount; i++)
        {
            try
            {
                // -- 1. 自检
                if ( !serverState.hasServerConn(timeout_option) )
                {
                    if ( !serverState.getState().equals(State.excption) )
                    {
                        serverState.setState(State.disConn_option);
                    }
                }
                else
                {
                    if ( serverState.getState().equals(State.disConn_option) )
                    {
                        resume(serverState);
                    }
                }
                
                // -- 2. 心跳
                service.heartbeat(serverState);
                
                // -- 3. 检举:检查其他服务器是否有与数据库心跳超时的，超时则改变其状态表中状态
                service.impeach(timeout_db);
                
                // -- 4. 选举:检查自己的zone，状态正常的服务器中优先级最高的服务器是否处于working状态，不是则更改其状态
                service.vote(serverState);
                
                // -- 5. 再次自检：检查自己是否在选举的时候状态被改变：1. working -> hotspare , 2. hotspare -> working
                String dbState = service.selfChecking(serverState);
                if ( !serverState.getState().name().equals(dbState) )
                {
                    if ( State.working.name().equals(dbState) )
                    {
                        // -- 5-1. hotspare -> working
                        serverState.setState(State.working);
                    }
                    else if ( State.hotspare.name().equals(dbState) )
                    {
                        // -- 5-2. working -> hotspare
                        serverState.setState(State.hotspare);
                    }
                }
                
                // -- DB心跳异常恢复
                if ( serverState.getState().equals(State.excption) )
                {
                    MyLog.serverLog.warn("  --  @心跳:DB  --  心跳成功，服务器状态恢复为 : ");
                    service.heartbeat(serverState);
                    resume(serverState);
                }
                
                // -- 日志数量控制
                if ( disconnCount > 0 )
                {
                    disconnCount = 0;
                }
                
                break;
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
            }
            
            if ( i == retryCount - 1 )
            {
                if ( disconnCount < disConnPrintLogCount )
                {
                    MyLog.serverLog.warn("  --  @心跳:DB  --  心跳失败，服务器状态置为 : " + State.excption.name());
                }
                
                disconnCount++;
                
                serverState.setState(State.excption);
            }
        }
    }
    
    public void resume(ServerState serverState)
    {
        State state = serverState.getState();
        
        if ( state.equals(State.excption) )
        {
        
        }
    }
    
}
