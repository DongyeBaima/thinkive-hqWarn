package com.thinkive.hqwarn.task.serverstate;

/**@����: ���������:��������������Ƿ��������ݿ�������ʱ�ģ���ʱ��ı���״̬����״̬
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��8��15�� ����10:22:35
 */
public class ServerImpeachTask extends AbstractServerStateTask
{
    
    private final int timeout_db = 2;
    
    @Override
    public void Execute()
    {
        serverStateService.impeach(timeout_db);
    }
    
}
