package com.thinkive.hqwarn.function;

import java.util.HashMap;
import java.util.Map;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.RequestImpl;
import com.thinkive.web.bean.Response;

/**
 * @描述: 用户信息数据
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年6月21日 下午6:16:14
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
