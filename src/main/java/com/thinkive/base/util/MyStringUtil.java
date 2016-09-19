package com.thinkive.base.util;

import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @描述: 字符串帮助类
 * @版权: Copyright (c) 2015
 * @公司: Thinkive
 * @作者: 王嵊俊
 * @创建日期: 2015年8月16日 下午12:05:09
 */
public class MyStringUtil
{
    
    public static Log logger = LogFactory.getLog("Server");
    
    /**
     * @描述: double转化float,防止直接转化精度丢失问题
     * @作者: 王嵊俊
     * @创建日期: 2015年12月23日 下午3:09:14
     * @param 
     * @return
     */
    public static double floatToDouble(float f)
    {
        return Double.parseDouble(f + "");
    }
    
    /**
     * @描述: 安全的字符串转double类型
     * @作者: 王嵊俊
     * @创建日期: 2015年8月18日 下午2:30:04
     * @param str
     * @return
     */
    public static double strToDouble(String str)
    {
        try
        {
            if ( isNullOrEmpry(str) )
                return 0;
                
            str = str.trim();
            
            if ( isDouble(str) )
                return Double.parseDouble(str);
                
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        return 0;
    }
    
    /**
     * @描述: 安全的字符串转float类型
     * @作者: 王嵊俊
     * @创建日期: 2015年8月18日 下午2:30:04
     * @param str
     * @return
     */
    public static float strToFloat(String str)
    {
        try
        {
            if ( isNullOrEmpry(str) )
                return 0;
                
            str = str.trim();
            
            if ( isDouble(str) )
                return Float.parseFloat(str);
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        return 0;
    }
    
    /**
     * @描述: 安全的字符串转int类型
     * @作者: 王嵊俊
     * @创建日期: 2015年8月18日 下午2:30:04
     * @param str
     * @return
     */
    public static int strToInt(String str)
    {
        try
        {
            if ( isNullOrEmpry(str) )
                return 0;
                
            str = str.trim();
            
            if ( isNumber(str) )
                return Integer.parseInt(str);
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        return 0;
    }
    
    /**
     * @描述: 安全的字符串转long类型
     * @作者: 王嵊俊
     * @创建日期: 2015年8月18日 下午2:30:04
     * @param str
     * @return
     */
    public static long strToLong(String str)
    {
        try
        {
            if ( isNullOrEmpry(str) )
                return 0;
                
            str = str.trim();
            
            if ( isNumber(str) )
                return Long.parseLong(str);
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        return 0;
    }
    
    /**
     * @描述: 安全的字符串转short类型
     * @作者: 王嵊俊
     * @创建日期: 2015年8月18日 下午2:30:04
     * @param str
     * @return
     */
    public static short strToShort(String str)
    {
        try
        {
            if ( isNullOrEmpry(str) )
                return 0;
                
            str = str.trim();
            
            if ( isNumber(str) )
                return Short.parseShort(str);
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        return 0;
    }
    
    /**
     * @描述: 使用正则表达式判断字符串是否由数字构成
     * @作者: 王嵊俊
     * @创建日期: 2015年8月16日 上午11:56:58
     * @param str
     * @return
     */
    public static boolean isNumber(String str)
    {
        try
        {
            if ( isNullOrEmpry(str) )
                return false;
                
            Pattern pattern = Pattern.compile("^[+-]?(?:[0-9][0-9]*|0)");
            
            return pattern.matcher(str).matches();
            
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        return false;
    }
    
    /**
     * @描述: 使用正则表达式判断字符串是否由字母构成
     * @作者: 王嵊俊
     * @创建日期: 2015年8月16日 上午11:56:58
     * @param str
     * @return
     */
    public static boolean isLetter(String str)
    {
        try
        {
            if ( isNullOrEmpry(str) )
                return false;
                
            Pattern pattern = Pattern.compile("[[a-z]|[A-Z]]*");
            return pattern.matcher(str).matches();
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        
        return false;
    }
    
    /**
     * @描述:
     * @作者: 王嵊俊
     * @创建日期: 2015年8月18日 下午2:22:31
     * @param str
     * @return
     */
    public static boolean isDouble(String str)
    {
        try
        {
            if ( isNullOrEmpry(str) )
                return false;
                
            Pattern pattern = Pattern.compile("^[+-]?(?:[1-9][0-9]*|0)(?:.[0-9]+){0,1}");
            return pattern.matcher(str).matches();
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        
        return false;
    }
    
    /**
     * @描述:判断字符串是否为空或这是否为null
     * @作者: 王嵊俊
     * @创建日期: 2015年8月18日 下午1:36:34
     * @param str
     * @return
     */
    public static boolean isNullOrEmpry(String str)
    {
        try
        {
            if ( str == null || "".equals(str.trim()) )
            {
                return true;
            }
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        return false;
    }
    
    /**
     * @描述: 转化成格式输出
     * @作者: 王嵊俊
     * @创建日期: 2016年5月16日 下午2:32:54
     * @param params
     * @return
     */
    public static String getString(Map<String, ?> params)
    {
        StringBuilder builder = new StringBuilder("	");
        
        int size = params.size();
        
        for (String key : params.keySet())
        {
            builder.append("[" + key + " : ");
            builder.append(params.get(key) + "]");
            
            if ( --size > 0 )
            {
                builder.append("	,	");
            }
        }
        
        return builder.toString();
    }
    
}
