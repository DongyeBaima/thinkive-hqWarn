package com.thinkive.hqwarn.task.serverstate;

import com.thinkive.warn.entity.State;

/**@����: �������Լ�
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��8��15�� ����10:22:35
 */
public class SelfInspectionTask extends AbstractServerStateTask
{
    private final int timeout_option = 3000; //����ѡ�ɷ�����������ʱʱ�� �� ��λms��
    
    @Override
    public void Execute()
    {
        // TODO Auto-generated method stub
        
        if ( serverState.getState().equals(State.initializing) )
        {
            return;
        }
        
        // -- ����Ƿ�����ѡ�ɷ��������ӵ���̨���ѷ�����
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
        
        // -- ����Լ��Ƿ����������ʱ�����������������Ϊexception״̬
        String dbState = serverStateService.selfChecking(serverState);
        if ( State.excption.name().equals(dbState) )
        {
            serverStateService.heartbeat(serverState);
        }
        
    }
    
}
