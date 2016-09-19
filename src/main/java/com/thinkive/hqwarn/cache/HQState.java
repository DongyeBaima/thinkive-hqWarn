package com.thinkive.hqwarn.cache;

import java.util.HashMap;
import java.util.Map;

import com.thinkive.hqwarn.util.ServerConfig;

/**
 * @����: ���� ״̬
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��10�� ����5:44:22
 */
public class HQState
{
    private static final Map<String, HQState> hqStateMap = initMap();
                                                         
    public static final HQState               HS         = hqStateMap.get(ServerConfig.HQ_MARKET_HS);
                                                         
    public static final HQState               HK         = hqStateMap.get(ServerConfig.HQ_MARKET_HK);
                                                         
    public static HQState get(String market)
    {
        if ( ServerConfig.HQ_MARKET_SH.equals(market) || ServerConfig.HQ_MARKET_SZ.equals(market) )
        {
            return HS;
        }
        
        return hqStateMap.get(market);
    }
    
    private static Map<String, HQState> initMap()
    {
        Map<String, HQState> map = new HashMap<>();
        map.put(ServerConfig.HQ_MARKET_HK, new HQState());
        
        HQState hsState = new HQState();
        map.put(ServerConfig.HQ_MARKET_HS, hsState);
        map.put(ServerConfig.HQ_MARKET_SH, hsState);
        map.put(ServerConfig.HQ_MARKET_SZ, hsState);
        
        return map;
    }
    
    private HQState()
    {
    
    }
    
    private long           SourceTime;
                           
    /**
     * ת��� �����г���ʼ������
     */
    private String         currentDate;
                           
    /**
     * ת�������ʱ��/��ת������س�ʼ�����ʱ��
     */
    private String         convDate;
                           
    private boolean        isInit            = false;
                                             
    private static boolean convertConnStatus = false; // ת�������״̬
                                             
    private static boolean gatewayConnStatus = false; // ��������״̬
                                             
    public long getSourceTime()
    {
        return SourceTime;
    }
    
    public String getCurrentDate()
    {
        return currentDate;
    }
    
    public String getConvDate()
    {
        return convDate;
    }
    
    public static boolean isConvertConnStatus()
    {
        return convertConnStatus;
    }
    
    public static boolean isGatewayConnStatus()
    {
        return gatewayConnStatus;
    }
    
    public static void setConvertConnStatus(boolean convertConnStatus)
    {
        HQState.convertConnStatus = convertConnStatus;
    }
    
    public static void setGatewayConnStatus(boolean gatewayConnStatus)
    {
        HQState.gatewayConnStatus = gatewayConnStatus;
    }
    
    public boolean isInit()
    {
        return isInit;
    }
    
    public void setSourceTime(long SourceTime)
    {
        if ( this.SourceTime < SourceTime )
        {
            this.SourceTime = SourceTime;
        }
    }
    
    public void setCurrentDate(String currentDate)
    {
        this.currentDate = currentDate;
    }
    
    public void setConvDate(String convDate)
    {
        this.convDate = convDate;
    }
    
    public void setInit(boolean isInit)
    {
        this.isInit = isInit;
    }
    
}
