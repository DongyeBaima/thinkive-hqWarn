package com.thinkive.hqwarn.function;

import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**
 * @����: ������ӿ�
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��5�� ����10:47:32
 */
public interface IFunction
{
    public void service(Request req, Response resp);
}
