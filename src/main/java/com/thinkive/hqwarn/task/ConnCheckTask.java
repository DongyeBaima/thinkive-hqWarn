package com.thinkive.hqwarn.task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.thinkive.hqwarn.cache.NetConnManager;
import com.thinkive.hqwarn.netty.GateWayDao;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.market.bean.MCState;
import com.thinkive.timerengine.Task;

/**
 * @����: ״̬����߳�
 * @��Ȩ: Copyright (c) 2013
 * @��˾: ˼�ϿƼ�
 * @����: ��֪֮
 * @�汾: 1.0
 * @��������: 2013-11-5
 * @����ʱ��: ����3:13:33
 */
public class ConnCheckTask implements Task
{
    private static Log logger = LogFactory.getLog("Server");
    
    private void checkGateWayConnState()
    {
        List<InetSocketAddress> addressList = ServerConfig.ADDRESS_LIST_GATEWAY;
        
        if ( addressList == null || addressList.isEmpty() )
        {
            logger.warn("	--	@������	--	û�п����ӵġ��������ء�����Ч�ġ��������ء���ַ���ã����������ļ�	������");
            return;
        }
        
        long curDbfTime = 0;
        // ��ȡ��ǰ������dbf�ļ���ʱ��
        InetSocketAddress curAddress = NetConnManager.GATEWAY.getConnAddress();
        if ( curAddress == null )
        {
            curAddress = NetConnManager.GATEWAY.changeAddress();
            curDbfTime = getDbfTimeByAddress(curAddress);
            if ( curDbfTime > 0 )
            {
                logger.warn("	-- @���ӵ�ַ  --  ���������ء� ��ǰ���ӵ�ַ : �� " + curAddress + " ��");
            }
        }
        else
        {
            curDbfTime = getDbfTimeByAddress(curAddress);
            if ( curDbfTime == 0 )
            {
                NetConnManager.GATEWAY.changeAddress();
            }
        }
        // ��ѯ�ҵ�dbf���µ����ķ��������ӳ�������1���ӵ�
        for (InetSocketAddress inetSocketAddress : addressList)
        {
            if ( inetSocketAddress.equals(curAddress) )
            {
                continue;
            }
            
            long dbfTime = getDbfTimeByAddress(inetSocketAddress);
            if ( dbfTime - curDbfTime > 60 * 1000 )
            {
                logger.warn("	-- @���ӵ�ַ  --  ���������ء� ��ǰ���ӵ�ַ : �� " + curAddress + " ��");
                break;
            }
        }
    }
    
    /**
     * @������ ���������ַ��ȡdbf�ļ���ʱ��
     * 
     * @���ߣ���֪֮
     * @ʱ�䣺2012-4-6 ����3:28:48
     * @param socketAddress
     * @return
     * @throws IOException
     */
    private long getDbfTimeByAddress(InetSocketAddress socketAddress)
    {
        MCState state = null;
        long dbfTime = 0;
        
        try
        {
            state = GateWayDao.queryMCState(socketAddress);
            if ( state != null )
            {
                dbfTime = state.getDbfTime();
            }
        }
        catch (Exception e)
        {
            logger.warn("  --  @������	--	���������ء������쳣��	��ַ: ��  " + socketAddress + " ��", e);
        }
        
        return dbfTime;
    }
    
    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        checkGateWayConnState();
    }
}
