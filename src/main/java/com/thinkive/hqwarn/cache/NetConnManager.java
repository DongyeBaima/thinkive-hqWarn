package com.thinkive.hqwarn.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.bean.NetConnAddress;
import com.thinkive.hqwarn.util.ServerConfig;

/**
 * @描述: 连接管理
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月13日 下午2:09:05
 */
public class NetConnManager
{
    private static final Log                              logger  = LogFactory.getLog("Server");
                                                                  
    public static final NetConnAddress<String>            PUSH    = new NetConnAddress<>();
                                                                  
    public static final NetConnAddress<InetSocketAddress> GATEWAY = new NetConnAddress<>();
                                                                  
    public static final NetConnAddress<InetSocketAddress> ZMJ     = new NetConnAddress<>();
                                                                  
    public static void init()
    {
        if ( ServerConfig.ADDRESS_LIST_GATEWAY == null || ServerConfig.ADDRESS_LIST_GATEWAY.isEmpty() )
        {
            logger.warn("	--	@系统初始化	--	配置错误，没有可用的 【行情网关】地址");
        }
        
        if ( ServerConfig.ADDRESS_LIST_ZMJ == null || ServerConfig.ADDRESS_LIST_ZMJ.isEmpty() )
        {
            logger.warn("	--	@系统初始化	--	配置错误，没有可用的 【转码机】地址");
        }
        
        if ( ServerConfig.ADDRESS_LIST_PUSH != null )
        {
            PUSH.setAddressList(ServerConfig.ADDRESS_LIST_PUSH);
        }
        
        GATEWAY.setAddressList(ServerConfig.ADDRESS_LIST_GATEWAY);
        
        ZMJ.setAddressList(ServerConfig.ADDRESS_LIST_ZMJ);
    }
    
    /**
     * @描述: 网络断开重连模块
     * @作者: 王嵊俊
     * @创建日期: 2016年7月11日 下午2:11:05
     */
    public static InetSocketAddress reconnect(NetConnAddress<InetSocketAddress> netConnAddress)
    {
        long interval = 100;
        int defaultCount = netConnAddress.size() * 2;
        int index = 0;
        
        Socket socket = null;
        
        while (true)
        {
            index++;
            InetSocketAddress target = netConnAddress.changeAddress();
            
            try
            {
                socket = new Socket(target.getHostString(), target.getPort());
                
                return target;
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                
                MyLog.serverLog.error("	--	@连接异常	--	" + target, e);
                
                if ( index >= defaultCount )
                {
                    interval = ServerConfig.RECONN_INTERVAL;
                }
                
                try
                {
                    Thread.sleep(interval);
                }
                catch (InterruptedException e1)
                {
                    // TODO Auto-generated catch block
                    // ignor
                }
                
            }
            finally
            {
                if ( socket != null )
                {
                    try
                    {
                        socket.close();
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        // ignor
                    }
                }
            }
        }
    }
    
}
