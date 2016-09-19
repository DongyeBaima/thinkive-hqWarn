package com.thinkive;

import com.thinkive.base.config.Configuration;
import com.thinkive.base.util.StringHelper;
import com.thinkive.hqwarn.cache.NetConnManager;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IServerStateService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.TimeZone;

/**
 * @描述: 启动控制
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月4日 上午10:13:00
 */
public class AppServerT
{
    private static final Log logger  = LogFactory.getLog("Server");
                                     
    private static boolean   IsStart = false;
                                     
    public static void main(String[] args)
    {
        jettyStart(); // 启动jetty服务器
        start();
    }
    
    public static void start()
    {
        if ( IsStart )
            return;
        else
            IsStart = true;
            
        startWarnServer();
    }
    
    /**
     * @描述: 启动jetty服务器
     * @作者: 王嵊俊
     * @创建日期: 2015年10月20日 下午2:17:36
     */
    public static void jettyStart()
    {
        String jettyPort = Configuration.getString("jetty.port");
        if ( StringHelper.isNotEmpty(jettyPort) )
        {
            new Thread()
            {
                public void run()
                {
                    JettyServer server;
                    try
                    {
                        server = new JettyServer();
                        server.start();
                        
                    }
                    catch (Exception e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                };
            }.start();
            logger.warn("	--	@系统初始化	--	jetty服务器启动在[" + jettyPort + "]端口");
        }
        else
        {
            logger.warn("	--	@系统初始化	--	jetty服务未启动-port-[" + jettyPort + "]未配置");
        }
    }
    
    /**
     * @描述: 启动服务器
     * @作者: 王嵊俊
     * @创建日期: 2015年10月20日 下午2:15:39
     */
    public static void startWarnServer()
    {
        try
        {
            String timezone = Configuration.getString("general.timezone");
            if ( StringHelper.isNotEmpty(timezone) )
            {
                TimeZone.setDefault(TimeZone.getTimeZone(timezone));
            }
            logger.warn("	--	@系统初始化	--	当前时区 【 " + TimeZone.getDefault() + " 】");
            
            Init();
        }
        catch (Exception ex)
        {
            logger.error("	--	@系统初始化	--	启动服务器出错: ", ex);
            System.exit( -1);
        }
    }
    
    public static void Init()
    {
        NetConnManager.init();
        BeanFactory.init();
        
        // -- -- 向数据库注册
        IServerStateService serverStateService = BeanFactory.getBean(Key.ServerStateService);
        ServerState serverState = BeanFactory.getBean(Key.ServerState);
        serverStateService.register(serverState);
    }
    
}
