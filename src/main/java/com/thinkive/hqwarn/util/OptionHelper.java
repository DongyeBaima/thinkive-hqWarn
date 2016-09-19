package com.thinkive.hqwarn.util;

/**
 * @����: ��ѡ����ذ�����
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��6�� ����10:11:04
 */
public class OptionHelper
{
    /**
     * @����: �� stockCodeList ����� stockCode;
     * @����: ���ӿ�
     * @��������: 2016��5��6�� ����10:12:51
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
     * @����: ���ַ��� stockCodeList ��ɾ��stockcode
     * @����: ���ӿ�
     * @��������: 2016��5��6�� ����10:14:51
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
