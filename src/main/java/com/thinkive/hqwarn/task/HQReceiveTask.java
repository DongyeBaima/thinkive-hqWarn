package com.thinkive.hqwarn.task;

import com.thinkive.market.service.conn.TCPReceiver;
import com.thinkive.timerengine.Task;
import com.thinkive.timerengine.TaskManager;

/**
 * 描述 : 实时行情接受程序（连接转码机）
 * 版权 : Copyright-(c) 2016
 * 公司 : Thinkive
 *
 * @auter 王嵊俊
 * @create 2016-09-13 14:26
 */
public class HQReceiveTask implements Task
{

    @Override
    public void execute()
    {
        // -- 转码机接受程序
        TCPReceiver receiver = new TCPReceiver();
        receiver.start();
        TaskManager.start();
    }
}
