package com.thinkive.hqwarn.function;

import java.util.Map;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

public class Function200002 extends AbstractFunction
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
        
        errorNo = checkStockCode(req, params);
        if ( errorNo != FuncHelper.SUCCESS )
        {
            return errorNo;
        }
        
        errorNo = checkWarnType(req, params);
        if ( errorNo == FuncHelper.ERROR_NULL_WARNTYPE )
        {
            return FuncHelper.SUCCESS_IGNORE;
            
        }
        else
        {
            return errorNo;
        }
        
    }
    
    @Override
    protected void service(Map<String, Object> params, Response resp) throws Exception
    {
        // TODO Auto-generated method stub
        
        UserInfo user = (UserInfo) params.get(FuncHelper.PARAM_USERID);
        String stockCode = (String) params.get(FuncHelper.PARAM_STOCKCODE);
        int[] warnTypes = (int[]) params.get(FuncHelper.PARAM_WARNTYPE);
        
        if ( user == null )
        {
            return;
        }
        
        WarnInfo warnInfo = null;
        for (int warnType : warnTypes)
        {
            warnInfo = user.getWarnInfo(stockCode, warnType);
            
            if ( warnInfo != null )
            {
                warnInfo.setDelete(true);
            }
        }
    }
    
}
