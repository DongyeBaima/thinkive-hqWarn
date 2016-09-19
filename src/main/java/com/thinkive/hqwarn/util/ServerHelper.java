package com.thinkive.hqwarn.util;

import java.text.SimpleDateFormat;

/**
 * @描述: 服务器帮补类
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月4日 下午7:25:17
 */
public class ServerHelper
{
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 时间格式工具
    
    /**
     * @描述: 当前时间 格式: yyyy-MM-dd HH:mm:ss
     * @作者: 王嵊俊
     * @创建日期: 2016年5月4日 下午7:26:01
     * @return
     */
    public static final String nowTime()
    {
        return sdf.format(System.currentTimeMillis());
    }
    
}
