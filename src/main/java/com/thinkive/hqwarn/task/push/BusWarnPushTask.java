package com.thinkive.hqwarn.task.push;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import com.thinkive.base.bus.BusClientService;
import com.thinkive.base.config.Configuration;
import com.thinkive.base.util.MyLog;
import com.thinkive.gateway.v2.result.Result;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnPushInfo;
import com.thinkive.hqwarn.cache.ServerCache;

/**@描述: 推送任务：Bus通道
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年8月30日 下午4:30:19
 */
public class BusWarnPushTask extends AbstractWarnPushTask
{
    private final String BUS_SERVER_NAME      = Configuration.getString("PushServer.BusName", "PushServer");
                                              
    @SuppressWarnings("unused")
    @Deprecated
    private final String BUS_PUSH_PRODUCTNAME = Configuration.getString("PushServer.ProductName");
                                              
    private final int    BUS_FUNCNO           = 1003620;
                                              
    public BusWarnPushTask()
    {
    }
    
    protected Map<String, Object> getParams(WarnPushInfo pushInfo)
    {
        // TODO Auto-generated method stub
        Map<String, Object> paramMap = new HashMap<>();
        UserInfo user = ServerCache.getUser(pushInfo.getWarnInfo().getUserID());
        int osId = user.getOsId();
        String token = user.getPushMark(osId);
        Stock stock = pushInfo.getStock();
        String content = "[" + stock.getName() + "]" + pushInfo.getContent();
        
        Map<String, Object> extParamMap = new HashMap<>();
        extParamMap.put("stockCode", stock.getCode());
        extParamMap.put("stockName", stock.getName());
        extParamMap.put("stockMarket", stock.getMarket());
        extParamMap.put("stockType", stock.getStktype());
        extParamMap.put("title", content);
        //        extParamMap.put("productName", BUS_PUSH_PRODUCTNAME);
        
        if ( osId != 1 && osId != 2 )
        {
            if ( token.length() == 64 )
            {
                osId = 1;
            }
            else
            {
                osId = 2;
            }
        }
        
        paramMap.put("deviceType", osId);
        paramMap.put("deviceId", token);
        paramMap.put("content", content);
        paramMap.put("params", JSON.toString(extParamMap));
        
        return paramMap;
    }
    
    @Override
    protected boolean sendPushMessage(WarnPushInfo pushInfo)
    {
        // TODO Auto-generated method stub
        Map<String, ?> requestParams = getParams(pushInfo);
        Result result = BusClientService.invokeBus(BUS_SERVER_NAME, BUS_FUNCNO, requestParams);
        
        MyLog.pushLog.warn("   --  @推送消息   --  用户：" + pushInfo.getWarnInfo().getUserID());
        MyLog.pushLog.warn("   --  @推送消息   --  请求：" + requestParams);
        MyLog.pushLog.warn("   --  @推送消息   --  返回：" + result.getErr_info());
        MyLog.pushLog.warn("");
        
        if ( result.getErr_no() == 0 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**@描述: 测试推送
     * @作者: 王嵊俊
     * @创建日期: 2016年9月6日 下午12:10:07
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> extMap = new HashMap<>();
        extMap.put("stockCode", "000568");
        extMap.put("stockName", "泸州老窖");
        extMap.put("stockMarket", "SZ");
        extMap.put("stockType", 1);
        extMap.put("title", "[泸州老窖]当前股价已达到31.67,跌幅超-0.72%");
        extMap.put("productName", "思迪综合APP标准版");
        
        paramMap.put("deviceType", 1);
        paramMap.put("deviceId", "573c9995b6ccf249563efcf730d1414e7d968d95336d771b1792af0ff57f16f8");
        paramMap.put("content", "[泸州老窖]当前股价已达到31.67,跌幅超-0.72%");
        paramMap.put("params", JSON.toString(extMap));
        
        Result result = BusClientService.invokeBus(Configuration.getString("PushServer.BusName", "PushServer"), 1003620,
                paramMap);
        System.out.println(result.getErr_info());
        
    }
    
}
