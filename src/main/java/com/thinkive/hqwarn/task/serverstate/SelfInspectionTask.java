package com.thinkive.hqwarn.task.serverstate;

import com.thinkive.warn.entity.State;

/**@描述: 服务器自检
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年8月15日 上午10:22:35
 */
public class SelfInspectionTask extends AbstractServerStateTask
{
    private final int timeout_option = 3000; //与自选股服务器心跳超时时间 ， 单位ms；
    
    @Override
    public void Execute()
    {
        // TODO Auto-generated method stub
        
        if ( serverState.getState().equals(State.initializing) )
        {
            return;
        }
        
        // -- 检查是否有自选股服务器连接到此台提醒服务器
        if ( !serverState.hasServerConn(timeout_option) )
        {
            if ( !(serverState.getState().equals(State.excption) || serverState.getState().equals(State.initializing)) )
            {
                serverState.setState(State.disConn_option);
            }
        }
        else
        {
            if ( serverState.getState().equals(State.disConn_option) )
            {
                stateChangeService.disConn_option2hotspare();
            }
        }
        
        // -- 检查自己是否存在心跳超时，被其他服务器检举为exception状态
        String dbState = serverStateService.selfChecking(serverState);
        if ( State.excption.name().equals(dbState) )
        {
            serverStateService.heartbeat(serverState);
        }
        
    }
    
}
