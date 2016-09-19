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
 * @����: ���ӹ���
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��13�� ����2:09:05
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
            logger.warn("	--	@ϵͳ��ʼ��	--	���ô���û�п��õ� ���������ء���ַ");
        }
        
        if ( ServerConfig.ADDRESS_LIST_ZMJ == null || ServerConfig.ADDRESS_LIST_ZMJ.isEmpty() )
        {
            logger.warn("	--	@ϵͳ��ʼ��	--	���ô���û�п��õ� ��ת�������ַ");
        }
        
        if ( ServerConfig.ADDRESS_LIST_PUSH != null )
        {
            PUSH.setAddressList(ServerConfig.ADDRESS_LIST_PUSH);
        }
        
        GATEWAY.setAddressList(ServerConfig.ADDRESS_LIST_GATEWAY);
        
        ZMJ.setAddressList(ServerConfig.ADDRESS_LIST_ZMJ);
    }
    
    /**
     * @����: ����Ͽ�����ģ��
     * @����: ���ӿ�
     * @��������: 2016��7��11�� ����2:11:05
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
                
                MyLog.serverLog.error("	--	@�����쳣	--	" + target, e);
                
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
