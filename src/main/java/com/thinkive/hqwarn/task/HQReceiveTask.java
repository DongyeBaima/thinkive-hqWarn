package com.thinkive.hqwarn.task;

import com.thinkive.market.service.conn.TCPReceiver;
import com.thinkive.timerengine.Task;
import com.thinkive.timerengine.TaskManager;

/**
 * ���� : ʵʱ������ܳ�������ת�����
 * ��Ȩ : Copyright-(c) 2016
 * ��˾ : Thinkive
 *
 * @auter ���ӿ�
 * @create 2016-09-13 14:26
 */
public class HQReceiveTask implements Task
{

    @Override
    public void execute()
    {
        // -- ת������ܳ���
        TCPReceiver receiver = new TCPReceiver();
        receiver.start();
        TaskManager.start();
    }
}
