package com.thinkive.hqwarn.function;

import java.util.Map;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**
 * @描述: 修改自选股
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月6日 下午4:14:19
 */
@Deprecated
public class Function100003 extends AbstractFunction
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
            // -- 股票列表为空 返回-1 不执行 方法service()
            return FuncHelper.SUCCESS_IGNORE;
        }
    }
    
    @Override
    protected void service(Map<String, Object> params, Response resp) throws Exception
    {
        // TODO Auto-generated method stub
        UserInfo user = (UserInfo) params.get(FuncHelper.PARAM_USERID);
        String stockCodes[] = (String[]) params.get(FuncHelper.PARAM_STOCKCODELIST);
        
        Map<String, Map<Integer, WarnInfo>> warnInfoMap = user.getWarnInfoMap();
        
        Map<Integer, WarnInfo> map = null;
        
        boolean isDelete;
        
        for (String stockCode : warnInfoMap.keySet())
        {
            isDelete = true;
            for (String code : stockCodes)
            {
                if ( stockCode.equals(code) )
                {
                    isDelete = false;
                    break;
                }
            }
            
            if ( isDelete )
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
    
}
