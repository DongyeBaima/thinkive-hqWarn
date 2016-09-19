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
 * ����:
 * ��Ȩ:	 Copyright (c) 2005
 * ��˾:	 ˼�ϿƼ�
 * ����:	 �����
 * �汾:	 1.0
 * ��������: 2006-10-11
 * ����ʱ��: 11:13:50
 */
public class ApplicationLifecycleListener implements ServletContextListener
{
    
    private static final Log logger = LogFactory.getLog("Server");
    
    /**
     * ��ϵͳ����ʱ����
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
     * ϵͳ����ʱ��ʼ����Ӧ������
     */
    private void init()
    {
        // OLDAppServer.start();
        try
        {
            AppServer.start();
        } catch (Exception e)
        {
            MyLog.serverLog.error(" --  @ϵͳ����   --  ϵͳ����ʧ�ܣ�����",e);
            System.out.println(" --  @ϵͳ����   --  ϵͳ����ʧ�ܣ�����    "+e.getMessage());
        }

    }
    
}
