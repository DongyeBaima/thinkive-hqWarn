package com.thinkive.hqwarn.function;

import java.util.HashMap;
import java.util.Map;

import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**
 * @描述: 心跳包
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年6月21日 下午6:16:14
 */
public class Function999999 extends AbstractFunction
{
    
    @Override
    protected int processParams(Request request, Map<String, Object> params)
    {
        // TODO Auto-generated method stub
        
        int errorNo = checkSererId(request, params);
        if ( errorNo != FuncHelper.SUCCESS )
        {
            return errorNo;
        }
        
        return checkZoneId(request, params);
    }
    
    @Override
    protected void service(Map<String, Object> params, Response response) throws Exception
    {
        // TODO Auto-generated method stub
        String serverId = (String) params.get(FuncHelper.HEART_PARAM_SERVERID);
        
        int zoneId = (int) params.get(FuncHelper.HEART_PARAM_ZONEID);
        
        if ( zoneId != ServerConfig.SERVER_ZONEID )
        {
            MyLog.serverLog.error(" --  @系统配置   --  配置错误，请检查！！！，本机配置的ZoneID为：" + ServerConfig.SERVER_ZONEID
                    + ",而在自选股服务器[" + serverId + "]配置的是:" + zoneId);
            response.setErrorNo(FuncHelper.ERROR_ILLEGAL_ZONEID);
            return;
        }
        
        serverState.serverTalk(serverId);
        
        String mySererId = serverState.getServerId();
        String state = serverState.getState().name();
        
        Map<String, Object> resultMap = new HashMap<>();
        
        resultMap.put(FuncHelper.HEART_PARAM_STATE, state);
        resultMap.put(FuncHelper.HEART_PARAM_SERVERID, mySererId);
        
        response.write(resultMap);
    }
    
}
