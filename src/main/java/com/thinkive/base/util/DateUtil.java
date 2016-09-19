package com.thinkive.base.util;

import java.text.SimpleDateFormat;

public class DateUtil
{
    public static final SimpleDateFormat TimeFormat   = new SimpleDateFormat("HH:mm:ss");
                                                      
    public static final SimpleDateFormat DateFormat   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                      
    public static final SimpleDateFormat MinuteFormat = new SimpleDateFormat("HHmm");
                                                      
    /**
     * @描述: 将毫秒数格式化为 HH:mm:ss
     * @作者: 王嵊俊
     * @创建日期: 2016年1月4日 下午7:30:33
     * @param time
     * @return
     */
    public static final String FormateTime(long time)
    {
        return TimeFormat.format(time);
    }
    
    /**
     * @描述: 将毫秒数格式化为 yyyy-MM-dd HH:mm:ss
     * @作者: 王嵊俊
     * @创建日期: 2016年1月4日 下午7:38:03
     * @param date
     * @return
     */
    public static final String FormateDate(long date)
    {
        return DateFormat.format(date);
    }
    
    /**
     * @描述: 将毫秒数格式化为 HHmm
     * @作者: 王嵊俊
     * @创建日期: 2016年1月20日 下午8:12:00
     * @param millis
     * @return
     */
    public static final String FormateMinute(long millis)
    {
        return MinuteFormat.format(millis);
    }
    
    /**
     * @描述: 将毫秒数格式化为  formate
     * @作者: 王嵊俊
     * @创建日期: 2016年1月4日 下午7:41:13
     * @return
     */
    public static final String FormateDate(long time, String formate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        
        return sdf.format(time);
    }
    
}
