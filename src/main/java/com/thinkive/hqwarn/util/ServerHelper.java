package com.thinkive.hqwarn.util;

import java.text.SimpleDateFormat;

/**
 * @����: �������ﲹ��
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��4�� ����7:25:17
 */
public class ServerHelper
{
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ʱ���ʽ����
    
    /**
     * @����: ��ǰʱ�� ��ʽ: yyyy-MM-dd HH:mm:ss
     * @����: ���ӿ�
     * @��������: 2016��5��4�� ����7:26:01
     * @return
     */
    public static final String nowTime()
    {
        return sdf.format(System.currentTimeMillis());
    }
    
}
