package com.thinkive.hqwarn.function;

import java.util.HashMap;
import java.util.Map;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.RequestImpl;
import com.thinkive.web.bean.Response;

/**
 * @����: �û���Ϣ����
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��6��21�� ����6:16:14
 */
public class Function900002 extends AbstractFunction
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
        
        String userID = (String) params.get("UserID");
        
        Map<String, Map<String, Object>> result = new HashMap<>();
        Map<String, Object> items = null;
        
        if ( userID == null )
        {
            for (String id : ServerCache.getuserInfoCache().keySet())
            {
                items = new HashMap<>();
                items.put("UserInfo", ServerCache.getUser(id));
                items.put("WarnInfo", ServerCache.getUser(id).getWarnInfoMap());
                result.put(id, items);
            }
            response.write(result);
        }
        else
        {
            items = new HashMap<>();
            items.put("UserInfo", ServerCache.getUser(userID));
            items.put("WarnInfo", ServerCache.getUser(userID).getWarnInfoMap());
            
            response.write(items);
        }
        
    }
    
}
