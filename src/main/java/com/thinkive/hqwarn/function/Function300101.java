package com.thinkive.hqwarn.function;

import java.util.Map;

import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**@描述: 添加用户 绑定设备
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年9月3日 下午7:02:56
 */
public class Function300101 extends AbstractFunction
{
    
    @Override
    protected int processParams(Request req, Map<String, Object> params)
    {
        // TODO Auto-generated method stub
        int result = checkUserID(req, params);
        if ( result != FuncHelper.SUCCESS )
        {
            return result;
        }
        result = checkOSID(req, params);
        if ( result == FuncHelper.ERROR_NULL_OS_ID )
        {
            return FuncHelper.SUCCESS_IGNORE;
        }
        return result;
    }
    
    @Override
    protected void service(Map<String, Object> params, Response resp) throws Exception
    {
        // TODO Auto-generated method stub
        // -- 方法checkUserID() 已插入了新用户
        UserInfo user = (UserInfo) params.get(FuncHelper.PARAM_USERID);
        int osid = (int) params.get(FuncHelper.PARAM_OS_ID);
        String token = (String) params.get(FuncHelper.PARAM_TOKEN);
        String mobileId = (String) params.get(FuncHelper.PARAM_MOBILEID);
        
        if ( osid == 1 )
        {
            user.setOsId(osid);
            user.setToken(token);
        }
        else if ( osid == 2 )
        {
            user.setOsId(osid);
            user.setToken(mobileId);
        }
    }
    
}
