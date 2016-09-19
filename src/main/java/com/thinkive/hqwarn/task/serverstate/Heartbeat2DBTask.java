package com.thinkive.hqwarn.task.serverstate;

/**@描述: 心跳 --> DB
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年8月15日 上午10:22:35
 */
public class Heartbeat2DBTask extends AbstractServerStateTask
{
    @Override
    public void Execute()
    {
        // TODO Auto-generated method stub
        serverStateService.heartbeat(serverState);
        
        //        MyLog.serverLog.warn(" --  --- --- --  当前服务器状态为：" + serverState.getState());
    }
}
