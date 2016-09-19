package com.thinkive.warn.service.impl;

import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IStateChangeService;

/**@����: ״̬�ı����
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��8��15�� ����3:11:38
 */
public class StateChangeService implements IStateChangeService
{
    private final ServerState serverState = BeanFactory.getBean(Key.ServerState);
    
    @Override
    public void disConn_option2hotspare()
    {
        boolean init = ServerCache.init();
        
        if ( init )
        {
            if ( serverState.getState().equals(State.disConn_option) )
            {
                serverState.setState(State.hotspare);
            }
        }
    }
}
