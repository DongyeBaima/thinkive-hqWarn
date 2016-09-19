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
 * @描述: 状态检测线程
 * @版权: Copyright (c) 2013
 * @公司: 思迪科技
 * @作者: 岳知之
 * @版本: 1.0
 * @创建日期: 2013-11-5
 * @创建时间: 下午3:13:33
 */
public class ConnCheckTask implements Task
{
    private static Log logger = LogFactory.getLog("Server");
    
    private void checkGateWayConnState()
    {
        List<InetSocketAddress> addressList = ServerConfig.ADDRESS_LIST_GATEWAY;
        
        if ( addressList == null || addressList.isEmpty() )
        {
            logger.warn("	--	@网络检测	--	没有可连接的【行情网关】，无效的【行情网关】地址配置，请检查配置文件	！！！");
            return;
        }
        
        long curDbfTime = 0;
        // 获取当前服务器dbf文件的时间
        InetSocketAddress curAddress = NetConnManager.GATEWAY.getConnAddress();
        if ( curAddress == null )
        {
            curAddress = NetConnManager.GATEWAY.changeAddress();
            curDbfTime = getDbfTimeByAddress(curAddress);
            if ( curDbfTime > 0 )
            {
                logger.warn("	-- @连接地址  --  【行情网关】 当前连接地址 : 【 " + curAddress + " 】");
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
        // 轮询找到dbf更新的中心服务器，延迟相差大于1分钟的
        for (InetSocketAddress inetSocketAddress : addressList)
        {
            if ( inetSocketAddress.equals(curAddress) )
            {
                continue;
            }
            
            long dbfTime = getDbfTimeByAddress(inetSocketAddress);
            if ( dbfTime - curDbfTime > 60 * 1000 )
            {
                logger.warn("	-- @连接地址  --  【行情网关】 当前连接地址 : 【 " + curAddress + " 】");
                break;
            }
        }
    }
    
    /**
     * @描述： 根据网络地址获取dbf文件的时间
     * 
     * @作者：岳知之
     * @时间：2012-4-6 下午3:28:48
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
            logger.warn("  --  @网络检测	--	【行情网关】连接异常，	地址: 【  " + socketAddress + " 】", e);
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
