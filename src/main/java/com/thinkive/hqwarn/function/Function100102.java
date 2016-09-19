package com.thinkive.hqwarn.function;

import java.util.Map;

import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**
 * ������ɾ����ѡ����Ϣ ͬʱɾ����Ԥ��������ɾ��Ԥ����Ϣ
 * 
 * @author ����
 * @created 2016��8��11�� ����10:39:13
 * @since
 */
public class Function100102 extends AbstractFunction
{
    
    @Override
    protected int processParams(Request req, Map<String, Object> params)
    {
        // TODO Auto-generated method stub
        
        int errorNo = checkUserID(req, params);
        if ( errorNo != FuncHelper.SUCCESS )
        {
            return errorNo;
        }
        
        errorNo = checkStockCodeList(req, params);
        if ( errorNo != FuncHelper.ERROR_NULL_STOCKCODELIST )
        {
            return errorNo;
        }
        else
        {
            // -- ��Ʊ�б�Ϊ��  ����-1  ��ִ�� ����service() 
            return FuncHelper.SUCCESS_IGNORE;
        }
        
    }
    
    @Override
    protected void service(Map<String, Object> params, Response resp) throws Exception
    {
        UserInfo user = (UserInfo) params.get(FuncHelper.PARAM_USERID);
        String[] stockCodes = (String[]) params.get(FuncHelper.PARAM_STOCKCODELIST);
        
        Map<String, Map<Integer, WarnInfo>> warnInfoMap = user.getWarnInfoMap();
        
        Map<Integer, WarnInfo> map = null;
        
        for (String stockCode : stockCodes)
        {
            map = warnInfoMap.get(stockCode);
            if ( map != null )
            {
                for (WarnInfo warnInfo : map.values())
                {
                    warnInfo.setDelete(true);
                }
            }
        }
    }
}
