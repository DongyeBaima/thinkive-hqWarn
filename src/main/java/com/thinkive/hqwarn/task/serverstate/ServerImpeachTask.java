package com.thinkive.hqwarn.task.serverstate;

/**@描述: 服务器检举:检查其他服务器是否有与数据库心跳超时的，超时则改变其状态表中状态
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年8月15日 上午10:22:35
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
