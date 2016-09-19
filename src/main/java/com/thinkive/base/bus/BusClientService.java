package com.thinkive.base.bus;

import java.util.Map;

import com.thinkive.base.util.ConvertHelper;
import com.thinkive.base.util.MyLog;
import com.thinkive.gateway.v2.client.Client;
import com.thinkive.gateway.v2.result.Result;

public class BusClientService
{
    /**
     * ���췽��
     */
    private BusClientService()
    {
    
    }
    
    /**
     * ����:Bus���ù�������  
     * @param serverName bus����������
     * @param funcNo	   ���ܺ�
     * @param paraMap 	  ���paraMap	
     * @return
     */
    public static Result invokeBus(String serverName, int funcNo, Map<String, ?> paraMap)
    {
        Result result = new Result();
        MyLog.serverLog.debug("    --  @Bus����  --  ����[" + serverName + "]���ܺ�[" + funcNo + "]��ʼ,�������:" + paraMap);
        try
        {
            Client client = new Client(serverName);
            result = client.getResult(funcNo, paraMap);
        }
        catch (Exception e)
        {
            MyLog.serverLog.warn("    --  @Bus����  --  ����[" + serverName + "]���ܺ�[" + funcNo + "]�쳣������", e);
            result.setErr_no(getErrorNo(funcNo, 1));
            result.setErr_info("����[" + serverName + "]�쳣:" + e.getMessage());
        }
        finally
        {
            MyLog.serverLog
                    .debug("    --  @Bus����  --  ����[" + serverName + "]���ܺ�[" + funcNo + "]��Ӧ:" + result.getErr_info());
        }
        return result;
    }
    
    /**
     * ��������ȡ������ 
     * @param funcNo    ���ܺ�
     * @param errorNo   ������
     * @return  ���ܺ�+������
     */
    private static int getErrorNo(int funcNo, int errorNo)
    {
        if ( errorNo > 99 )
        {
            errorNo = 99;
        }
        if ( errorNo < 0 )
        {
            errorNo = 0;
        }
        if ( errorNo < 10 )
        {
            return ConvertHelper.strToInt("-" + funcNo + "0" + errorNo);
        }
        else
        {
            return ConvertHelper.strToInt("-" + funcNo + errorNo);
        }
    }
}
