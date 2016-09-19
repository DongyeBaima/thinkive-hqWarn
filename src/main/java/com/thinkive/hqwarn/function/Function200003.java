package com.thinkive.hqwarn.function;

import java.util.List;
import java.util.Map;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.hqwarn.util.WarnCacheHelper;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

public class Function200003 extends AbstractFunction
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
        
        errorNo = checkWarnInfo(req, params);
        if ( errorNo == FuncHelper.ERROR_NULL_WARNINFO )
        {
            return FuncHelper.SUCCESS_IGNORE;
        }
        else
        {
            return errorNo;
        }
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void service(Map<String, Object> params, Response resp) throws Exception
    {
        // TODO Auto-generated method stub
        
        UserInfo user = (UserInfo) params.get(FuncHelper.PARAM_USERID);
        String stockCode = (String) params.get(FuncHelper.PARAM_STOCKCODE);
        List<WarnInfo> warnInfoList = (List<WarnInfo>) params.get(FuncHelper.PARAM_WARNINFO);
        
        WarnInfo myWarnInfo = null;
        int warnType = 0;
        int warnCount = 0;
        float warnValue = 0;
        for (WarnInfo warnInfo : warnInfoList)
        {
            warnType = warnInfo.getWarnType();
            warnCount = warnInfo.getWarnCount();
            warnValue = warnInfo.getWarnValue();
            
            myWarnInfo = user.getWarnInfo(stockCode, warnType);
            
            if ( myWarnInfo != null )
            {
                if ( !warnInfo.isDelete() )
                {
                    if ( warnValue == myWarnInfo.getWarnValue() )
                    {
                        // -- -- û�иı�����ֵ�����
                        
                        if ( warnCount != myWarnInfo.getWarnCount() )
                        {
                            myWarnInfo.setWarnCount(warnCount);
                            myWarnInfo.setValidWarnCount(warnCount);
                        }
                        continue;
                    }
                }
                
                myWarnInfo.setDelete(true);
            }
            
            if ( !warnInfo.isDelete() )
            {
                warnInfo.setStockCode(stockCode);
                warnInfo.setUserID(user.getUserID());
                WarnCacheHelper.cacheWarnInfo(user, warnInfo);
            }
        }
        
    }
    
}
