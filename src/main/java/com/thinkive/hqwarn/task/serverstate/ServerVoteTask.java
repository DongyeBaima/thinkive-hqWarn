package com.thinkive.hqwarn.task.serverstate;

import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IServerStateService;

/**@描述: 服务器选举:检查自己的zone，状态正常的服务器中优先级最高的服务器是否处于working状态，不是则更改其状态
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年8月15日 上午10:22:35
 */
public class ServerVoteTask extends AbstractServerStateTask
{
    private final IServerStateService service     = BeanFactory.getBean(Key.ServerStateService);
                                                  
    private final ServerState         serverState = BeanFactory.getBean(Key.ServerState);
                                                  
    @Override
    public void Execute()
    {
        // -- 选举:检查自己的zone，状态正常的服务器中优先级最高的服务器是否处于working状态，不是则更改其状态
        if ( serverState.getState().equals(State.working) || serverState.getState().equals(State.hotspare) )
        {
            service.vote(serverState);
        }
        
    }
}
