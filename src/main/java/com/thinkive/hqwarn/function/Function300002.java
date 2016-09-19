package com.thinkive.hqwarn.function;

import java.util.Map;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

public class Function300002 extends AbstractFunction
{
    
    @Override
    protected int processParams(Request req, Map<String, Object> params)
    {
        // TODO Auto-generated method stub
        
        return checkUserID(req, params);
    }
    
    @Override
    protected void service(Map<String, Object> params, Response resp) throws Exception
    {
        // TODO Auto-generated method stub
        
        UserInfo user = (UserInfo) params.get(FuncHelper.PARAM_USERID);
        
        if ( user == null )
        {
            return;
        }
        
        Map<String, Map<Integer, WarnInfo>> warnInfoMap = user.getWarnInfoMap();
        
        for (Map<Integer, WarnInfo> map : warnInfoMap.values())
        {
            for (WarnInfo warnInfo : map.values())
            {
                warnInfo.setDelete(true);
            }
        }
    }
}
