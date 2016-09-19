package com.thinkive.hqwarn.util;

import java.text.DecimalFormat;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * @描述: 触发判断器  工具类
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年6月16日 下午1:53:53
 */
public class JudgerHelper
{
    
    private static int[]               PRECISION_2_STKTYPE = { 0, 1, 2, 6, 7, 8, 9, 14, 15, 17, 18, 20, 25, 27, 26 };
                                                           
    private static final DecimalFormat decimalFormat3      = new DecimalFormat("#.000");
                                                           
    private static final DecimalFormat decimalFormat2      = new DecimalFormat("#.00");
                                                           
    /**
     * @描述: 格式化 股票价格，普通股票保留2为小数，基金保留3位
     * @作者: 王嵊俊
     * @创建日期: 2016年6月16日 下午1:55:41
     * @param stock
     * @return
     */
    public static String formatStockPrice(Stock stock)
    {
        
        if ( is2Precision(stock) )
        {
            return formatFloat(stock.getNow(), decimalFormat2);
        }
        else
        {
            return formatFloat(stock.getNow(), decimalFormat3);
        }
    }
    
    /**
     * @描述: 格式化 股票涨跌幅 
     * @作者: 王嵊俊
     * @创建日期: 2016年6月16日 下午3:53:26
     * @param stock
     * @return
     */
    public static String formatStockUppercent(Stock stock)
    {
        return formatFloat(stock.getUppercent(), decimalFormat2);
    }
    
    /**
     * @描述: 格式化 提醒值， 默认2位 
     * @作者: 王嵊俊
     * @创建日期: 2016年6月16日 下午3:53:26
     * @param stock
     * @return
     */
    public static String formatWarnInfoValue(WarnInfo warnInfo)
    {
        return formatFloat(warnInfo.getWarnValue(), decimalFormat2);
    }
    
    /**
     * @描述: 格式化 浮点数
     * @作者: 王嵊俊
     * @创建日期: 2016年6月16日 下午3:52:06
     * @param value
     * @param decimalFormat
     * @return
     */
    public static String formatFloat(float value, DecimalFormat decimalFormat)
    {
        String s = decimalFormat.format(value);
        
        if ( s.startsWith(".") )
        {
            s = "0" + s;
        }
        else if ( s.startsWith("-.") )
        {
            s = s.replace("-.", "-0.");
        }
        
        return s;
    }
    
    /**
     * @描述: 判断是否是保留2位小数的类型
     * @作者: 王嵊俊
     * @创建日期: 2016年6月16日 下午1:57:16
     * @param stock
     * @return
     */
    public static boolean is2Precision(Stock stock)
    {
        int stkType = stock.getStktype();
        for (int stkType_2 : PRECISION_2_STKTYPE)
        {
            if ( stkType == stkType_2 )
            {
                return true;
            }
        }
        
        return false;
    }
    
}
