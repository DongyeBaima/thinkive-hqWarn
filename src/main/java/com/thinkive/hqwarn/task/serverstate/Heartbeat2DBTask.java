package com.thinkive.hqwarn.task.serverstate;

/**@����: ���� --> DB
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��8��15�� ����10:22:35
 */
public class Heartbeat2DBTask extends AbstractServerStateTask
{
    @Override
    public void Execute()
    {
        // TODO Auto-generated method stub
        serverStateService.heartbeat(serverState);
        
        //        MyLog.serverLog.warn(" --  --- --- --  ��ǰ������״̬Ϊ��" + serverState.getState());
    }
}
