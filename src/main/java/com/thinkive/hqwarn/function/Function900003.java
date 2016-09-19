package com.thinkive.hqwarn.function;

import java.util.Map;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.RequestImpl;
import com.thinkive.web.bean.Response;

/**
 * @描述: 行情数据
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年6月21日 下午6:16:14
 */
public class Function900003 extends AbstractFunction
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
        
        String stockCode = (String) params.get("StockCode");
        
        if ( stockCode == null )
        {
            response.write(ServerCache.getHQCache());
        }
        else
        {
            response.write(ServerCache.getStock(stockCode));
        }
        
    }
    
}
