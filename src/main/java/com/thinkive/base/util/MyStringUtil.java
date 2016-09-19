package com.thinkive.base.util;

import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @����: �ַ���������
 * @��Ȩ: Copyright (c) 2015
 * @��˾: Thinkive
 * @����: ���ӿ�
 * @��������: 2015��8��16�� ����12:05:09
 */
public class MyStringUtil
{
    
    public static Log logger = LogFactory.getLog("Server");
    
    /**
     * @����: doubleת��float,��ֱֹ��ת�����ȶ�ʧ����
     * @����: ���ӿ�
     * @��������: 2015��12��23�� ����3:09:14
     * @param 
     * @return
     */
    public static double floatToDouble(float f)
    {
        return Double.parseDouble(f + "");
    }
    
    /**
     * @����: ��ȫ���ַ���תdouble����
     * @����: ���ӿ�
     * @��������: 2015��8��18�� ����2:30:04
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
     * @����: ��ȫ���ַ���תfloat����
     * @����: ���ӿ�
     * @��������: 2015��8��18�� ����2:30:04
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
     * @����: ��ȫ���ַ���תint����
     * @����: ���ӿ�
     * @��������: 2015��8��18�� ����2:30:04
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
     * @����: ��ȫ���ַ���תlong����
     * @����: ���ӿ�
     * @��������: 2015��8��18�� ����2:30:04
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
     * @����: ��ȫ���ַ���תshort����
     * @����: ���ӿ�
     * @��������: 2015��8��18�� ����2:30:04
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
     * @����: ʹ��������ʽ�ж��ַ����Ƿ������ֹ���
     * @����: ���ӿ�
     * @��������: 2015��8��16�� ����11:56:58
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
     * @����: ʹ��������ʽ�ж��ַ����Ƿ�����ĸ����
     * @����: ���ӿ�
     * @��������: 2015��8��16�� ����11:56:58
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
     * @����:
     * @����: ���ӿ�
     * @��������: 2015��8��18�� ����2:22:31
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
     * @����:�ж��ַ����Ƿ�Ϊ�ջ����Ƿ�Ϊnull
     * @����: ���ӿ�
     * @��������: 2015��8��18�� ����1:36:34
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
     * @����: ת���ɸ�ʽ���
     * @����: ���ӿ�
     * @��������: 2016��5��16�� ����2:32:54
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
