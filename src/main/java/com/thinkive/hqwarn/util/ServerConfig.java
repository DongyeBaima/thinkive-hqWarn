package com.thinkive.hqwarn.util;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import com.thinkive.base.config.Configuration;
import com.thinkive.base.util.ConvertHelper;
import com.thinkive.base.util.MyStringUtil;

/**
 * @描述: 系统配置
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月4日 上午10:40:58
 */
public class ServerConfig
{
    
    public static final String                  SERVER_ID                = Configuration
            .getString("WarnServer.ServerID");
            
    public static final int                     SERVER_PRIORITY          = Configuration.getInt("WarnServer.Priority",
            0);                                                                                                          //成为主机的优先级
            
    // -- -- -- -- -- -- -- -- -- -- 【 提醒服务器配置 】 -- -- -- -- -- -- -- -- -- --
    
    public static final int                     SERVER_ZONEID            = Configuration.getInt("WarnServer.ZoneID", 0); // 分区ID
                                                                         
    public static final boolean                 IS_DEBUG                 = Configuration.getInt("WarnServer.IsDebug",
            0) == 1;                                                                                                     // debug 模式
            
    public static final int                     PUSH_THREAD_COUNT        = Configuration
            .getInt("WarnServer.PushThreadCount", 3);                                                                    // 推送线程个数
            
    public static final int                     JUDGE_THREAD_COUNT       = Configuration
            .getInt("WarnServer.JudgeThreadCount", 3);                                                                   // 行情提醒触发线程个数
            
    public static final long                    RECONN_INTERVAL          = Configuration
            .getInt("WarnServer.ReconnInterval", 30) * 1000;                                                             // 网络异常重连间隔时间 (单位:S)
            
    // -- -- -- -- -- -- -- -- -- -- 【 推送服务器配置 】 -- -- -- -- -- -- -- -- -- --
    public static final String                  ADDRESS_LIST_PUSH[]      = Configuration.getString("PushServer.Address")
            .split("\\|");                                                                                               // 推送信息地址
            
    public static final int                     PUSH_TYPE                = Configuration.getInt("PushServer.PushType",
            1);                                                                                                          // 推送版本
            
    public static final int                     PUSH_RETRY_COUNT         = Configuration.getInt("PushSerer.RetryCount",
            1);                                                                                                          // 推送失败 重试次数 // 推送消息失败重试次数
            
    public static final int                     PUSH_TIME_HS[][]         = getPushTime("PushServer.HSPushTime");
                                                                         
    public static final int                     PUSH_TIME_HK[][]         = getPushTime("PushServer.HKPushTime");
                                                                         
    // -- -- -- -- -- -- -- -- -- -- 【 数据库相关配置 】 -- -- -- -- -- -- -- -- -- --
    public static final String                  DB_TABLENAME_USER        = "T_USERINFO";                                 // 用户信息表名
                                                                         
    public static final String                  DB_TABLENAME_OPTION      = "T_OPTIONSTOCK_";                             // 自选股表名（前缀）
                                                                         
    public static final String                  DB_TABLENAME_WARN        = "T_STOCKWARN_";                               // 行情提醒表名（前缀）
                                                                         
    // -- -- -- -- -- -- -- -- -- -- 【 行情配置 】 -- -- -- -- -- -- -- -- -- --
    
    public static final String                  HQ_MARKET_HK             = "HK";
                                                                         
    public static final String                  HQ_MARKET_HS             = "HS";
                                                                         
    public static final String                  HQ_MARKET_SH             = "SH";
                                                                         
    public static final String                  HQ_MARKET_SZ             = "SZ";
                                                                         
    public static final List<InetSocketAddress> ADDRESS_LIST_ZMJ         = getAddressList("HQ.ZMJHost");
                                                                         
    public static final List<InetSocketAddress> ADDRESS_LIST_GATEWAY     = getAddressList("HQ.GateWayHost");
                                                                         
    public static final int                     HQ_HISTORY_RETAIN_MINUTE = 3;                                            // 行情历史数据保持时间 分钟
                                                                         
    // -- -- -- -- -- -- -- -- -- -- 【 方法区 】 -- -- -- -- -- -- -- -- -- --
    
    private static final List<InetSocketAddress> getAddressList(String name)
    {
        String[] addressStrs = Configuration.getString(name, "").split("\\|");
        
        List<InetSocketAddress> addressList = new ArrayList<>();
        
        if ( addressStrs != null )
        {
            addressList = new ArrayList<InetSocketAddress>();
            for (int i = 0; i < addressStrs.length; i++)
            {
                String[] addressPort = addressStrs[i].split(":");
                if ( addressPort.length == 2 )
                {
                    String address = addressPort[0];
                    int port = ConvertHelper.strToInt(addressPort[1]);
                    addressList.add(new InetSocketAddress(address, port));
                }
            }
        }
        return addressList;
    }
    
    /**
     * @描述: 解析pushtime配置
     * @作者: 王嵊俊
     * @创建日期: 2016年6月15日 下午1:46:23
     * @param configName
     * @return
     */
    private static final int[][] getPushTime(String configName)
    {
        String pushTimeConfigs[] = Configuration.getString(configName, "0-2359").split("\\|");
        
        int pushTimes[][] = new int[pushTimeConfigs.length][2];
        
        String items[] = null;
        for (int i = 0, len = pushTimeConfigs.length; i < len; i++)
        {
            items = pushTimeConfigs[i].split("-");
            
            if ( items.length >= 2 )
            {
                pushTimes[i][0] = MyStringUtil.strToInt(items[0]);
                pushTimes[i][1] = MyStringUtil.strToInt(items[1]);
            }
            else
            {
                pushTimes[i][0] = MyStringUtil.strToInt(items[0]);
                pushTimes[i][1] = 0;
                
            }
        }
        
        return pushTimes;
    }
    
}
