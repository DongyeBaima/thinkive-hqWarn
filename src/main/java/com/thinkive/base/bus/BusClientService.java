package com.thinkive.base.bus;

import java.util.Map;

import com.thinkive.base.util.ConvertHelper;
import com.thinkive.base.util.MyLog;
import com.thinkive.gateway.v2.client.Client;
import com.thinkive.gateway.v2.result.Result;

public class BusClientService
{
    /**
     * 构造方法
     */
    private BusClientService()
    {
    
    }
    
    /**
     * 描述:Bus调用公共方法  
     * @param serverName bus服务器名称
     * @param funcNo	   功能号
     * @param paraMap 	  入参paraMap	
     * @return
     */
    public static Result invokeBus(String serverName, int funcNo, Map<String, ?> paraMap)
    {
        Result result = new Result();
        MyLog.serverLog.debug("    --  @Bus调用  --  调用[" + serverName + "]功能号[" + funcNo + "]开始,请求参数:" + paraMap);
        try
        {
            Client client = new Client(serverName);
            result = client.getResult(funcNo, paraMap);
        }
        catch (Exception e)
        {
            MyLog.serverLog.warn("    --  @Bus调用  --  调用[" + serverName + "]功能号[" + funcNo + "]异常！！！", e);
            result.setErr_no(getErrorNo(funcNo, 1));
            result.setErr_info("调用[" + serverName + "]异常:" + e.getMessage());
        }
        finally
        {
            MyLog.serverLog
                    .debug("    --  @Bus调用  --  调用[" + serverName + "]功能号[" + funcNo + "]响应:" + result.getErr_info());
        }
        return result;
    }
    
    /**
     * 描述：获取错误编号 
     * @param funcNo    功能号
     * @param errorNo   错误编号
     * @return  功能号+错误编号
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
