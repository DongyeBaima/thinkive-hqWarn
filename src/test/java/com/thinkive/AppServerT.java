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
 * @����: ��������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��4�� ����10:13:00
 */
public class AppServerT
{
    private static final Log logger  = LogFactory.getLog("Server");
                                     
    private static boolean   IsStart = false;
                                     
    public static void main(String[] args)
    {
        jettyStart(); // ����jetty������
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
     * @����: ����jetty������
     * @����: ���ӿ�
     * @��������: 2015��10��20�� ����2:17:36
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
            logger.warn("	--	@ϵͳ��ʼ��	--	jetty������������[" + jettyPort + "]�˿�");
        }
        else
        {
            logger.warn("	--	@ϵͳ��ʼ��	--	jetty����δ����-port-[" + jettyPort + "]δ����");
        }
    }
    
    /**
     * @����: ����������
     * @����: ���ӿ�
     * @��������: 2015��10��20�� ����2:15:39
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
            logger.warn("	--	@ϵͳ��ʼ��	--	��ǰʱ�� �� " + TimeZone.getDefault() + " ��");
            
            Init();
        }
        catch (Exception ex)
        {
            logger.error("	--	@ϵͳ��ʼ��	--	��������������: ", ex);
            System.exit( -1);
        }
    }
    
    public static void Init()
    {
        NetConnManager.init();
        BeanFactory.init();
        
        // -- -- �����ݿ�ע��
        IServerStateService serverStateService = BeanFactory.getBean(Key.ServerStateService);
        ServerState serverState = BeanFactory.getBean(Key.ServerState);
        serverStateService.register(serverState);
    }
    
}
