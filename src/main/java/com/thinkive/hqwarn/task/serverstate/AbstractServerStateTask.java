package com.thinkive.hqwarn.task.serverstate;

import com.thinkive.base.jdbc.exception.JdbcException;
import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.timerengine.Task;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IServerStateService;
import com.thinkive.warn.service.IStateChangeService;

public abstract class AbstractServerStateTask implements Task
{
    private static final int            retryCount           = 3;
                                                             
    private static final int            disConnPrintLogCount = 3;
                                                             
    private static int                  disconnCount         = 0;
                                                             
    protected final IServerStateService serverStateService   = BeanFactory.getBean(Key.ServerStateService);
                                                             
    protected final ServerState         serverState          = BeanFactory.getBean(Key.ServerState);
                                                             
    protected final IStateChangeService stateChangeService   = BeanFactory.getBean(Key.StateChangeService);
                                                             
    @Override
    public void execute()
    {
        try
        {
            Execute();
            
            // -- DB�쳣�ָ�
            if ( serverState.getState().equals(State.excption) )
            {
                serverState.setState(State.hotspare);
                MyLog.serverLog.warn("  --  @����:DB  --  �����ɹ���������״̬�ָ�Ϊ : " + State.hotspare.name());
                serverStateService.heartbeat(serverState);
            }
            
            // -- ��־��������
            if ( disconnCount > 0 )
            {
                disconnCount = 0;
            }
        }
        catch (JdbcException e)
        {
            MyLog.serverLog.warn("", e);
            
            disconnCount++;
            
            if ( disconnCount <= retryCount )
            {
                if ( disconnCount <= disConnPrintLogCount )
                {
                    MyLog.serverLog.warn("  --  @����:DB  --  ����ʧ�ܣ�������״̬��Ϊ : " + State.excption.name());
                }
                
                serverState.setState(State.excption);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            MyLog.serverLog.error("", e);
        }
    }
    
    protected abstract void Execute();
}
