package com.thinkive.hqwarn.heartbeat.impl;

import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.hqwarn.heartbeat.AbstractHeartbeat;
import com.thinkive.warn.entity.State;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IServerStateService;

@Deprecated
public class DBHeartbeat extends AbstractHeartbeat
{
    private final IServerStateService service              = BeanFactory.getBean(Key.ServerStateService);
                                                           
    private final ServerState         serverState          = BeanFactory.getBean(Key.ServerState);
                                                           
    private final int                 retryCount           = 3;                                          // ����ʧ�����Դ���
                                                           
    private final int                 timeout_db           = 2;                                          //�����������ݿ�������ʱʱ�䣬��λ��S                                              
                                                           
    private final int                 timeout_option       = 3000;                                       //����ѡ�ɷ�����������ʱʱ�� �� ��λms��
                                                           
    private final int                 disConnPrintLogCount = 3;                                          // �����ݿ�ʧȥ����3���ڴ�ӡ��־
                                                           
    private int                       disconnCount         = 0;                                          //�����ݿ�ʧȥ���Ӵ���
                                                           
    public DBHeartbeat(long idleTime)
    {
        // TODO Auto-generated constructor stub
        super(idleTime);
    }
    
    /* (non-Javadoc)
     * @see com.thinkive.hqwarn.heartbeat.IHeartBeat#heartbeat()
     */
    @Override
    public void heartbeat()
    {
        // TODO Auto-generated method stub
        
        for (int i = 0; i < retryCount; i++)
        {
            try
            {
                // -- 1. �Լ�
                if ( !serverState.hasServerConn(timeout_option) )
                {
                    if ( !serverState.getState().equals(State.excption) )
                    {
                        serverState.setState(State.disConn_option);
                    }
                }
                else
                {
                    if ( serverState.getState().equals(State.disConn_option) )
                    {
                        resume(serverState);
                    }
                }
                
                // -- 2. ����
                service.heartbeat(serverState);
                
                // -- 3. ���:��������������Ƿ��������ݿ�������ʱ�ģ���ʱ��ı���״̬����״̬
                service.impeach(timeout_db);
                
                // -- 4. ѡ��:����Լ���zone��״̬�����ķ����������ȼ���ߵķ������Ƿ���working״̬�������������״̬
                service.vote(serverState);
                
                // -- 5. �ٴ��Լ죺����Լ��Ƿ���ѡ�ٵ�ʱ��״̬���ı䣺1. working -> hotspare , 2. hotspare -> working
                String dbState = service.selfChecking(serverState);
                if ( !serverState.getState().name().equals(dbState) )
                {
                    if ( State.working.name().equals(dbState) )
                    {
                        // -- 5-1. hotspare -> working
                        serverState.setState(State.working);
                    }
                    else if ( State.hotspare.name().equals(dbState) )
                    {
                        // -- 5-2. working -> hotspare
                        serverState.setState(State.hotspare);
                    }
                }
                
                // -- DB�����쳣�ָ�
                if ( serverState.getState().equals(State.excption) )
                {
                    MyLog.serverLog.warn("  --  @����:DB  --  �����ɹ���������״̬�ָ�Ϊ : ");
                    service.heartbeat(serverState);
                    resume(serverState);
                }
                
                // -- ��־��������
                if ( disconnCount > 0 )
                {
                    disconnCount = 0;
                }
                
                break;
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
            }
            
            if ( i == retryCount - 1 )
            {
                if ( disconnCount < disConnPrintLogCount )
                {
                    MyLog.serverLog.warn("  --  @����:DB  --  ����ʧ�ܣ�������״̬��Ϊ : " + State.excption.name());
                }
                
                disconnCount++;
                
                serverState.setState(State.excption);
            }
        }
    }
    
    public void resume(ServerState serverState)
    {
        State state = serverState.getState();
        
        if ( state.equals(State.excption) )
        {
        
        }
    }
    
}
