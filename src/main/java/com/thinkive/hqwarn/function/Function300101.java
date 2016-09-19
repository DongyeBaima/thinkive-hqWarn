package com.thinkive.hqwarn.function;

import java.util.Map;

import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**@����: ����û� ���豸
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��9��3�� ����7:02:56
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
        // -- ����checkUserID() �Ѳ��������û�
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
