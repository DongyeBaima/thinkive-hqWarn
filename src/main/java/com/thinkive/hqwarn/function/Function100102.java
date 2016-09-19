package com.thinkive.hqwarn.function;

import java.util.Map;

import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**
 * 描述：删除自选股信息 同时删除从预警缓存中删除预警信息
 * 
 * @author 何洋
 * @created 2016年8月11日 上午10:39:13
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
            // -- 股票列表为空  返回-1  不执行 方法service() 
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
