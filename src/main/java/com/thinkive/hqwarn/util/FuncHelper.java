package com.thinkive.hqwarn.util;

import java.util.HashMap;
import java.util.Map;
import com.thinkive.hqwarn.bean.WarnInfo;

public class FuncHelper
{
    
    /** 没有返回集且操作成功的接口返回   */
    public static final String RESPONSE_SUCCESS            = "OK";
                                                           
    /** 响应包 返回集Key */
    public static final String RESPONSE_KEY_RESULT         = "results";
                                                           
    /** 响应包 错误号Key */
    public static final String RESPONSE_KEY_ERRORNO        = "errorNo";
                                                           
    /** 响应包 错误信息Key */
    public static final String RESPONSE_KEY_ERRORINFO      = "errorInfo";
                                                           
    // -- -- -- -- -- -- -- -- -- -- 【 心跳相关 】 -- -- -- -- -- -- -- -- -- --
    
    /** 心跳返回参数：状态*/
    public static final String HEART_PARAM_STATE           = "State";
                                                           
    /** 心跳返回参数：服务器ID*/
    public static final String HEART_PARAM_SERVERID        = "ServerId";
                                                           
    /** 心跳返回参数：服务器zoneID*/
    public static final String HEART_PARAM_ZONEID          = "ZoneId";
                                                           
    public static final int    HEART_FUNCNO                = 999999;
                                                           
    // -- -- -- -- -- -- -- -- -- -- 【 参数 】 -- -- -- -- -- -- -- -- -- --
    
    public static final String PARAM_USERID                = "UserID";
                                                           
    public static final String PARAM_STOCKCODELIST         = "StockCodeList";
                                                           
    public static final String PARAM_STOCKCODE             = "StockCode";
                                                           
    public static final String PARAM_WARNTYPE              = "WarnType";
                                                           
    public static final String PARAM_WARNINFO              = "WarnInfo";
                                                           
    public static final String PARAM_OS_ID                 = "OS_ID";
                                                           
    public static final String PARAM_MOBILEID              = "MobileID";
                                                           
    public static final String PARAM_TOKEN                 = "Token";
                                                           
    // -- -- -- -- -- -- -- -- -- -- 【 错误号 】 -- -- -- -- -- -- -- -- -- --
    
    public static final int    SUCCESS                     = 0;
                                                           
    public static final int    SUCCESS_IGNORE              = -1;                           // 成功，但忽略 ，内部使用
                                                           
    public static final int    ERROR_UNKNOWN               = 98;
                                                           
    public static final int    ERROR_DBERROR               = 99;
                                                           
    public static final int    ERROR_NOTINIT               = 97;
                                                           
    public static final int    ERROR_NULL_USERID           = 1;
                                                           
    public static final int    ERROR_ILLEGAL_USERID        = ERROR_NULL_USERID + 20;
                                                           
    public static final int    ERROR_NULL_STOCKCODELIST    = 2;
                                                           
    public static final int    ERROR_ILLEGAL_STOCKCODELIST = ERROR_NULL_STOCKCODELIST + 20;
                                                           
    public static final int    ERROR_NULL_STOCKCODE        = 3;
                                                           
    public static final int    ERROR_ILLEGAL_STOCKCODE     = ERROR_NULL_STOCKCODE + 20;
                                                           
    public static final int    ERROR_NULL_WARNTYPE         = 4;
                                                           
    public static final int    ERROR_ILLEGAL_WARNTYPE      = ERROR_NULL_WARNTYPE + 20;
                                                           
    public static final int    ERROR_NULL_WARNINFO         = 5;
                                                           
    public static final int    ERROR_ILLEGAL_WARNINFO      = ERROR_NULL_WARNINFO + 20;
                                                           
    public static final int    ERROR_NULL_SERVERID         = 6;
                                                           
    public static final int    ERROR_ILLEGAL_SERVERID      = ERROR_NULL_WARNINFO + 20;
                                                           
    public static final int    ERROR_NULL_ZONEID           = 7;
                                                           
    public static final int    ERROR_ILLEGAL_ZONEID        = ERROR_NULL_WARNINFO + 20;
                                                           
    //客户端系统
    public static final int    ERROR_NULL_OS_ID            = 13;
                                                           
    public static final int    ERROR_ILLEGAL_OS_ID         = ERROR_NULL_OS_ID + 20;
                                                           
    //设备ID
    public static final int    ERROR_NULL_MOBILEID         = 11;
                                                           
    public static final int    ERROR_ILLEGAL_MOBILEID      = ERROR_NULL_MOBILEID + 20;
                                                           
    //token
    public static final int    ERROR_NULL_TOKEN            = 12;
                                                           
    public static final int    ERROR_ILLEGAL_TOKEN         = ERROR_NULL_TOKEN + 20;
                                                           
    public static Map<Integer, String> laodErrorInfo()
    {
        Map<Integer, String> errorInfoMap = new HashMap<Integer, String>();
        
        errorInfoMap.put(ERROR_UNKNOWN, "未知错误");
        errorInfoMap.put(ERROR_DBERROR, "数据库操作失败");
        errorInfoMap.put(ERROR_NOTINIT, "系统未初始化，请稍后重试");
        
        errorInfoMap.put(ERROR_NULL_USERID, "参数 [UserID] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_USERID, "参数 [UserID] 不合法");
        
        errorInfoMap.put(ERROR_NULL_STOCKCODELIST, "参数  [StockCodeList] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_STOCKCODELIST, "参数  [StockCodeList] 不合法");
        
        errorInfoMap.put(ERROR_NULL_STOCKCODE, "参数  [StockCode] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_STOCKCODE, "参数  [StockCode] 不合法");
        
        errorInfoMap.put(ERROR_NULL_WARNTYPE, "参数  [WarnType] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_WARNTYPE, "参数  [WarnType] 不合法");
        
        errorInfoMap.put(ERROR_NULL_WARNINFO, "参数  [WarnInfo] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_WARNINFO, "参数  [WarnInfo] 不合法");
        
        errorInfoMap.put(ERROR_NULL_ZONEID, "参数  [ZoneID] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_ZONEID, "参数  [ZoneID] 不合法");
        
        errorInfoMap.put(ERROR_NULL_SERVERID, "参数  [ServerID] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_SERVERID, "参数  [ServerID] 不合法");
        
        errorInfoMap.put(ERROR_NULL_OS_ID, "参数  [OS_ID] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_OS_ID, "参数  [OS_ID] 不合法");
        
        errorInfoMap.put(ERROR_NULL_MOBILEID, "参数  [MobileID] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_MOBILEID, "参数  [MobileID] 不合法");
        
        errorInfoMap.put(ERROR_NULL_TOKEN, "参数  [Token] 不能为空");
        errorInfoMap.put(ERROR_ILLEGAL_TOKEN, "参数  [Token] 不合法");
        
        return errorInfoMap;
    }
    
    /**
     * @描述: 将提醒信息字符串 转换成对象
     * @作者: 王嵊俊
     * @创建日期: 2016年5月6日 下午3:41:27
     * @param sWarnInfo
     * @return
     */
    public static WarnInfo getWarnInfo(String sWarnInfo)
    {
        WarnInfo warnInfo = null;
        
        String warnInfoItem[] = sWarnInfo.split(":");
        if ( warnInfoItem.length == 3 )
        {
            warnInfo = new WarnInfo();
            
            int warnType = Integer.parseInt(warnInfoItem[0]);
            float warnValue = 0;
            if ( !warnInfoItem[1].isEmpty() )
            {
                warnValue = Float.parseFloat(warnInfoItem[1]);
            }
            else
            {
                warnInfo.setDelete(true);
            }
            int warnCount = Integer.parseInt(warnInfoItem[2]);
            
            warnInfo.setWarnType(warnType);
            warnInfo.setWarnValue(warnValue);
            warnInfo.setWarnCount(warnCount);
            warnInfo.setValidWarnCount(warnCount);
            
            // -- 王嵊俊 2016年6月17日 上午10:03:59 -- 【证通项目需求，可能存坑】
            if ( warnType == 4 )
            {
                warnValue = Math.abs(warnValue) * -1F;
                warnInfo.setWarnValue(warnValue);
            }
        }
        
        return warnInfo;
    }
    
}
