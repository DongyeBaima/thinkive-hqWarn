package com.thinkive.hqwarn.task.serverstate;

import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IServerStateService;

/**@����: ������ѡ��:����Լ���zone��״̬�����ķ����������ȼ���ߵķ������Ƿ���working״̬�������������״̬
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��8��15�� ����10:22:35
 */
public class ServerVoteTask extends AbstractServerStateTask
{
    private final IServerStateService service     = BeanFactory.getBean(Key.ServerStateService);
                                                  
    private final ServerState         serverState = BeanFactory.getBean(Key.ServerState);
                                                  
    @Override
    public void Execute()
    {
        // -- ѡ��:����Լ���zone��״̬�����ķ����������ȼ���ߵķ������Ƿ���working״̬�������������״̬
        if ( serverState.getState().equals(State.working) || serverState.getState().equals(State.hotspare) )
        {
            service.vote(serverState);
        }
        
    }
}
