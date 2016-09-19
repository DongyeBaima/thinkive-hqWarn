package com.thinkive.hqwarn.invoker;

/**
 * ����:
 * ��Ȩ:	 Copyright (c) 2007
 * ��˾:	 ˼�ϿƼ�
 * ����:	 �����
 * �汾:	 1.0
 * ��������: 2009-1-9
 * ����ʱ��: 17:29:18
 */
public class InvokeException extends Exception
{
    static final long serialVersionUID = -3387516993124229356L;
    
    public InvokeException()
    {
        super();
    }
    
    public InvokeException(String message)
    {
        super(message);
    }
    
    public InvokeException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public InvokeException(Throwable cause)
    {
        super(cause);
    }
}
