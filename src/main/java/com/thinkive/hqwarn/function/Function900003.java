package com.thinkive.hqwarn.function;

import java.util.Map;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.RequestImpl;
import com.thinkive.web.bean.Response;

/**
 * @����: ��������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��6��21�� ����6:16:14
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
