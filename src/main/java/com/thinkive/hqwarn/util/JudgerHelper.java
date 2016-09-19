package com.thinkive.hqwarn.util;

import java.text.DecimalFormat;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * @����: �����ж���  ������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��6��16�� ����1:53:53
 */
public class JudgerHelper
{
    
    private static int[]               PRECISION_2_STKTYPE = { 0, 1, 2, 6, 7, 8, 9, 14, 15, 17, 18, 20, 25, 27, 26 };
                                                           
    private static final DecimalFormat decimalFormat3      = new DecimalFormat("#.000");
                                                           
    private static final DecimalFormat decimalFormat2      = new DecimalFormat("#.00");
                                                           
    /**
     * @����: ��ʽ�� ��Ʊ�۸���ͨ��Ʊ����2ΪС����������3λ
     * @����: ���ӿ�
     * @��������: 2016��6��16�� ����1:55:41
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
     * @����: ��ʽ�� ��Ʊ�ǵ��� 
     * @����: ���ӿ�
     * @��������: 2016��6��16�� ����3:53:26
     * @param stock
     * @return
     */
    public static String formatStockUppercent(Stock stock)
    {
        return formatFloat(stock.getUppercent(), decimalFormat2);
    }
    
    /**
     * @����: ��ʽ�� ����ֵ�� Ĭ��2λ 
     * @����: ���ӿ�
     * @��������: 2016��6��16�� ����3:53:26
     * @param stock
     * @return
     */
    public static String formatWarnInfoValue(WarnInfo warnInfo)
    {
        return formatFloat(warnInfo.getWarnValue(), decimalFormat2);
    }
    
    /**
     * @����: ��ʽ�� ������
     * @����: ���ӿ�
     * @��������: 2016��6��16�� ����3:52:06
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
     * @����: �ж��Ƿ��Ǳ���2λС��������
     * @����: ���ӿ�
     * @��������: 2016��6��16�� ����1:57:16
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
