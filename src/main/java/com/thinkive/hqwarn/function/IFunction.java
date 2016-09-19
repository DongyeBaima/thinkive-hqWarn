package com.thinkive.hqwarn.function;

import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**
 * @描述: 请求处理接口
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月5日 上午10:47:32
 */
public interface IFunction
{
    public void service(Request req, Response resp);
}
