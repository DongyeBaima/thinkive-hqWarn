/*
 * Copyright (c) 2006 Your Corporation. All Rights Reserved.
 */

package com.thinkive.web.listener;

import com.thinkive.AppServer;
import com.thinkive.base.util.MyLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 描述:
 * 版权:	 Copyright (c) 2005
 * 公司:	 思迪科技
 * 作者:	 易庆锋
 * 版本:	 1.0
 * 创建日期: 2006-10-11
 * 创建时间: 11:13:50
 */
public class ApplicationLifecycleListener implements ServletContextListener
{
    
    private static final Log logger = LogFactory.getLog("Server");
    
    /**
     * 在系统启动时调用
     *
     * @param event a ServletContextEvent instance
     */
    public void contextInitialized(ServletContextEvent event)
    {
        if ( logger.isInfoEnabled() )
            logger.info("Starting application......");
            
        event.getServletContext();
        
        init();
    }
    
    public void contextDestroyed(javax.servlet.ServletContextEvent servletContextEvent)
    {
    
    }
    
    /**
     * 系统启动时初始化相应的数据
     */
    private void init()
    {
        // OLDAppServer.start();
        try
        {
            AppServer.start();
        } catch (Exception e)
        {
            MyLog.serverLog.error(" --  @系统启动   --  系统启动失败！！！",e);
            System.out.println(" --  @系统启动   --  系统启动失败！！！    "+e.getMessage());
        }

    }
    
}
