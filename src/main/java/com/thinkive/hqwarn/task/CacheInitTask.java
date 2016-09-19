package com.thinkive.hqwarn.task;

import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.timerengine.Task;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;

/**
 * @����: ������Ϣ��������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��9�� ����4:21:34
 */
public class CacheInitTask implements Task
{
    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        MyLog.serverLog.warn("	--	@�����ʼ������	--	��ʼ����");
        boolean init = ServerCache.init();
        
        if ( init )
        {
            ServerState serverState = BeanFactory.getBean(Key.ServerState);
            serverState.setState(State.hotspare);
            MyLog.serverLog.warn("	--	@�����ʼ������	--	�������,ϵͳ״̬��Ϊ��" + State.hotspare);
        }
        else
        {
            MyLog.serverLog.warn("	--	@�����ʼ������	--	����ʧ�ܣ�ϵͳ�˳�");
            System.exit( -1);
        }
    }
}
