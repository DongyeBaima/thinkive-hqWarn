package com.thinkive.hqwarn.function;

import java.util.HashMap;
import java.util.Map;

import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**
 * @����: ������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��6��21�� ����6:16:14
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
            MyLog.serverLog.error(" --  @ϵͳ����   --  ���ô������飡�������������õ�ZoneIDΪ��" + ServerConfig.SERVER_ZONEID
                    + ",������ѡ�ɷ�����[" + serverId + "]���õ���:" + zoneId);
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
