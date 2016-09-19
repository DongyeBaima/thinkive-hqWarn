package com.thinkive.hqwarn.util;

import java.util.HashMap;
import java.util.Map;
import com.thinkive.hqwarn.bean.WarnInfo;

public class FuncHelper
{
    
    /** û�з��ؼ��Ҳ����ɹ��Ľӿڷ���   */
    public static final String RESPONSE_SUCCESS            = "OK";
                                                           
    /** ��Ӧ�� ���ؼ�Key */
    public static final String RESPONSE_KEY_RESULT         = "results";
                                                           
    /** ��Ӧ�� �����Key */
    public static final String RESPONSE_KEY_ERRORNO        = "errorNo";
                                                           
    /** ��Ӧ�� ������ϢKey */
    public static final String RESPONSE_KEY_ERRORINFO      = "errorInfo";
                                                           
    // -- -- -- -- -- -- -- -- -- -- �� ������� �� -- -- -- -- -- -- -- -- -- --
    
    /** �������ز�����״̬*/
    public static final String HEART_PARAM_STATE           = "State";
                                                           
    /** �������ز�����������ID*/
    public static final String HEART_PARAM_SERVERID        = "ServerId";
                                                           
    /** �������ز�����������zoneID*/
    public static final String HEART_PARAM_ZONEID          = "ZoneId";
                                                           
    public static final int    HEART_FUNCNO                = 999999;
                                                           
    // -- -- -- -- -- -- -- -- -- -- �� ���� �� -- -- -- -- -- -- -- -- -- --
    
    public static final String PARAM_USERID                = "UserID";
                                                           
    public static final String PARAM_STOCKCODELIST         = "StockCodeList";
                                                           
    public static final String PARAM_STOCKCODE             = "StockCode";
                                                           
    public static final String PARAM_WARNTYPE              = "WarnType";
                                                           
    public static final String PARAM_WARNINFO              = "WarnInfo";
                                                           
    public static final String PARAM_OS_ID                 = "OS_ID";
                                                           
    public static final String PARAM_MOBILEID              = "MobileID";
                                                           
    public static final String PARAM_TOKEN                 = "Token";
                                                           
    // -- -- -- -- -- -- -- -- -- -- �� ����� �� -- -- -- -- -- -- -- -- -- --
    
    public static final int    SUCCESS                     = 0;
                                                           
    public static final int    SUCCESS_IGNORE              = -1;                           // �ɹ��������� ���ڲ�ʹ��
                                                           
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
                                                           
    //�ͻ���ϵͳ
    public static final int    ERROR_NULL_OS_ID            = 13;
                                                           
    public static final int    ERROR_ILLEGAL_OS_ID         = ERROR_NULL_OS_ID + 20;
                                                           
    //�豸ID
    public static final int    ERROR_NULL_MOBILEID         = 11;
                                                           
    public static final int    ERROR_ILLEGAL_MOBILEID      = ERROR_NULL_MOBILEID + 20;
                                                           
    //token
    public static final int    ERROR_NULL_TOKEN            = 12;
                                                           
    public static final int    ERROR_ILLEGAL_TOKEN         = ERROR_NULL_TOKEN + 20;
                                                           
    public static Map<Integer, String> laodErrorInfo()
    {
        Map<Integer, String> errorInfoMap = new HashMap<Integer, String>();
        
        errorInfoMap.put(ERROR_UNKNOWN, "δ֪����");
        errorInfoMap.put(ERROR_DBERROR, "���ݿ����ʧ��");
        errorInfoMap.put(ERROR_NOTINIT, "ϵͳδ��ʼ�������Ժ�����");
        
        errorInfoMap.put(ERROR_NULL_USERID, "���� [UserID] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_USERID, "���� [UserID] ���Ϸ�");
        
        errorInfoMap.put(ERROR_NULL_STOCKCODELIST, "����  [StockCodeList] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_STOCKCODELIST, "����  [StockCodeList] ���Ϸ�");
        
        errorInfoMap.put(ERROR_NULL_STOCKCODE, "����  [StockCode] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_STOCKCODE, "����  [StockCode] ���Ϸ�");
        
        errorInfoMap.put(ERROR_NULL_WARNTYPE, "����  [WarnType] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_WARNTYPE, "����  [WarnType] ���Ϸ�");
        
        errorInfoMap.put(ERROR_NULL_WARNINFO, "����  [WarnInfo] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_WARNINFO, "����  [WarnInfo] ���Ϸ�");
        
        errorInfoMap.put(ERROR_NULL_ZONEID, "����  [ZoneID] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_ZONEID, "����  [ZoneID] ���Ϸ�");
        
        errorInfoMap.put(ERROR_NULL_SERVERID, "����  [ServerID] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_SERVERID, "����  [ServerID] ���Ϸ�");
        
        errorInfoMap.put(ERROR_NULL_OS_ID, "����  [OS_ID] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_OS_ID, "����  [OS_ID] ���Ϸ�");
        
        errorInfoMap.put(ERROR_NULL_MOBILEID, "����  [MobileID] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_MOBILEID, "����  [MobileID] ���Ϸ�");
        
        errorInfoMap.put(ERROR_NULL_TOKEN, "����  [Token] ����Ϊ��");
        errorInfoMap.put(ERROR_ILLEGAL_TOKEN, "����  [Token] ���Ϸ�");
        
        return errorInfoMap;
    }
    
    /**
     * @����: ��������Ϣ�ַ��� ת���ɶ���
     * @����: ���ӿ�
     * @��������: 2016��5��6�� ����3:41:27
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
            
            // -- ���ӿ� 2016��6��17�� ����10:03:59 -- ��֤ͨ��Ŀ���󣬿��ܴ�ӡ�
            if ( warnType == 4 )
            {
                warnValue = Math.abs(warnValue) * -1F;
                warnInfo.setWarnValue(warnValue);
            }
        }
        
        return warnInfo;
    }
    
}
