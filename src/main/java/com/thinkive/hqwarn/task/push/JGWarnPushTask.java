package com.thinkive.hqwarn.task.push;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.bean.WarnPushInfo;
import com.thinkive.hqwarn.cache.NetConnManager;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.web.sentter.HttpSentter;

/**@描述: 推送任务 ：极光通道
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年8月30日 下午4:29:13
 */
public class JGWarnPushTask extends AbstractWarnPushTask
{
    
    private static final String PushParamType = "2"; // 推送请求 param 里的type
                                              
    private String              connAddress;
                                
    public JGWarnPushTask()
    {
        // TODO Auto-generated constructor stub
        connAddress = NetConnManager.PUSH.changeAddress();
    }
    
    protected Map<String, Object> getParams(WarnPushInfo pushInfo) throws UnsupportedEncodingException
    {
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        
        WarnInfo warnInfo = pushInfo.getWarnInfo();
        
        String userID = warnInfo.getUserID();
        String message = pushInfo.getMessage();
        int triggerFlag = 0;
        int type = ServerConfig.PUSH_TYPE;
        
        paramMap.put("userId", userID);
        paramMap.put("message", message);
        paramMap.put("type", type);
        paramMap.put("triggerFlag", triggerFlag);
        paramMap.put("params", params);
        
        params.put("type", PushParamType);
        params.put("data", data);
        
        data.put("title", pushInfo.getTitle());
        data.put("content", pushInfo.getContent());
        data.put("showTimeStr", pushInfo.getShowTimeStr());
        data.put("isRead", "");
        data.put("stockCode", pushInfo.getWarnInfo().getStockCode());
        data.put("stocktype", pushInfo.getStock().getStktype() + "");
        
        return paramMap;
    }
    
    @SuppressWarnings("unchecked")
    protected int processResult(String result)
    {
        try
        {
            Map<String, Object> map = (Map<String, Object>) JSON.parse(result);
            
            if ( map == null )
            {
                MyLog.serverLog.warn("	--	@提醒推送	--	推送返回结果不合法 ： " + result);
                return 0;
            }
            
            String returnCode = (String) map.get("returnCode");
            
            if ( returnCode == null )
            {
                MyLog.serverLog.warn("	--	@提醒推送	--	解析推送返回结果出错 ： " + result);
            }
            else
            {
                return Integer.parseInt(returnCode);
            }
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            MyLog.serverLog.warn("	--	@提醒推送	--	解析推送返回结果出错 ： " + result, e);
            return -1;
        }
        
        return 0;
    }
    
    protected boolean sendPushMessage(WarnPushInfo pushInfo)
    {
        try
        {
            Map<String, Object> paramMap = getParams(pushInfo);
            
            List<Map<String, Object>> paramList = new ArrayList<>();
            paramList.add(paramMap);
            String requestParams = JSON.toString(paramList);
            HttpSentter sentter = new HttpSentter(connAddress, "pushList=" + URLEncoder.encode(requestParams, "UTF-8"));
            
            String result = sentter.queryData();
            
            int returnCode = processResult(result);
            
            if ( returnCode == 1 )
            {
                MyLog.pushLog.debug("	--	@URI	--	" + connAddress);
                MyLog.pushLog.debug("	--	@请求	--	" + requestParams);
                MyLog.pushLog.debug("	--	@响应	--	" + result);
                MyLog.pushLog.debug("");
            }
            else
            {
                MyLog.pushLog.warn("	--	@URI	--	" + connAddress);
                MyLog.pushLog.warn("	--	@请求	--	" + requestParams);
                MyLog.pushLog.warn("	--	@响应	--	" + result);
                MyLog.pushLog.warn("");
            }
            
            switch (returnCode)
            {
                case 0:
                    return false;
                case 1:
                    return true;
                case 2:
                    MyLog.serverLog.warn("	--	@提醒推送	--	推送请求参数不合法 ： " + requestParams);
                    return false;
                default:
                    MyLog.serverLog.warn("	--	@提醒推送	--	未知返回值 ： " + returnCode);
                    return false;
            }
            
        }
        catch (IOException e)
        {
            // TODO: handle exception
            MyLog.serverLog.warn("	--	@提醒推送	--	【推送服务器】连接异常：" + connAddress, e);
            
            connAddress = NetConnManager.PUSH.changeAddress();
            
            return false;
            
        }
        catch (Exception e)
        {
            // TODO: handle exception
            MyLog.serverLog.warn("", e);
            
            return true;
        }
    }
}
