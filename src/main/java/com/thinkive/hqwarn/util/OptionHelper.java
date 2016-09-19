package com.thinkive.hqwarn.util;

/**
 * @描述: 自选股相关帮助类
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月6日 上午10:11:04
 */
public class OptionHelper
{
    /**
     * @描述: 向 stockCodeList 中添加 stockCode;
     * @作者: 王嵊俊
     * @创建日期: 2016年5月6日 上午10:12:51
     * @param stockCodeList
     * @param stockcode
     */
    public static final void addOptionStock(StringBuffer stockCodeList, String stockcode)
    {
        if ( stockCodeList.length() != 0 )
        {
            stockCodeList.append("|" + stockcode);
        }
        else
        {
            stockCodeList.append(stockcode);
        }
    }
    
    /**
     * @描述: 从字符串 stockCodeList 中删除stockcode
     * @作者: 王嵊俊
     * @创建日期: 2016年5月6日 上午10:14:51
     * @param stockCodeList
     * @param stockcode
     */
    public static final String deleteOptionStock(String stockCodeList, String stockcode)
    {
        int index = stockCodeList.indexOf(stockcode);
        
        if ( index == 0 )
        {
            stockCodeList = stockCodeList.replace(stockcode + "|", "");
        }
        else if ( index > 0 )
        {
            stockCodeList = stockCodeList.replace("|" + stockcode, "");
        }
        
        return stockCodeList;
    }
    
}
