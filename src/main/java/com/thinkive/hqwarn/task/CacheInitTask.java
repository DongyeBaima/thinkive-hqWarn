package com.thinkive.hqwarn.task;

import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.timerengine.Task;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;

/**
 * @描述: 提醒信息缓存任务
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月9日 下午4:21:34
 */
public class CacheInitTask implements Task
{
    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        MyLog.serverLog.warn("	--	@缓存初始化任务	--	开始任务");
        boolean init = ServerCache.init();
        
        if ( init )
        {
            ServerState serverState = BeanFactory.getBean(Key.ServerState);
            serverState.setState(State.hotspare);
            MyLog.serverLog.warn("	--	@缓存初始化任务	--	任务完成,系统状态置为：" + State.hotspare);
        }
        else
        {
            MyLog.serverLog.warn("	--	@缓存初始化任务	--	任务失败，系统退出");
            System.exit( -1);
        }
    }
}
