package com.thinkive.base.util;

import java.text.SimpleDateFormat;

public class DateUtil
{
    public static final SimpleDateFormat TimeFormat   = new SimpleDateFormat("HH:mm:ss");
                                                      
    public static final SimpleDateFormat DateFormat   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                      
    public static final SimpleDateFormat MinuteFormat = new SimpleDateFormat("HHmm");
                                                      
    /**
     * @����: ����������ʽ��Ϊ HH:mm:ss
     * @����: ���ӿ�
     * @��������: 2016��1��4�� ����7:30:33
     * @param time
     * @return
     */
    public static final String FormateTime(long time)
    {
        return TimeFormat.format(time);
    }
    
    /**
     * @����: ����������ʽ��Ϊ yyyy-MM-dd HH:mm:ss
     * @����: ���ӿ�
     * @��������: 2016��1��4�� ����7:38:03
     * @param date
     * @return
     */
    public static final String FormateDate(long date)
    {
        return DateFormat.format(date);
    }
    
    /**
     * @����: ����������ʽ��Ϊ HHmm
     * @����: ���ӿ�
     * @��������: 2016��1��20�� ����8:12:00
     * @param millis
     * @return
     */
    public static final String FormateMinute(long millis)
    {
        return MinuteFormat.format(millis);
    }
    
    /**
     * @����: ����������ʽ��Ϊ  formate
     * @����: ���ӿ�
     * @��������: 2016��1��4�� ����7:41:13
     * @return
     */
    public static final String FormateDate(long time, String formate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        
        return sdf.format(time);
    }
    
}
