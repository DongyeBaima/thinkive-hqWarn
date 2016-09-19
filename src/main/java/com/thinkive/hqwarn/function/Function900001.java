package com.thinkive.hqwarn.function;

import java.util.Map;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.RequestImpl;
import com.thinkive.web.bean.Response;

public class Function900001 extends AbstractFunction
{
    
    @Override
    protected int processParams(Request request, Map<String, Object> params)
    {
        // TODO Auto-generated method stub
        
        RequestImpl req = (RequestImpl) request;
        
        params.putAll(req.getParameter());
        return 0;
    }
    
    @Override
    protected void service(Map<String, Object> params, Response response) throws Exception
    {
        // TODO Auto-generated method stub
        String stockCode = (String) params.get(FuncHelper.PARAM_STOCKCODE);
        Integer warnType = (Integer) params.get("WarnType");
        
        if ( stockCode == null )
        {
            response.write(ServerCache.getWarnInfoCache());
        }
        else
        {
            if ( warnType == null )
            {
                response.write(ServerCache.getWarnInfoCache(stockCode));
                return;
            }
            
            response.write(ServerCache.getWarnInfoCache(stockCode, warnType));
        }
    }
    
}
