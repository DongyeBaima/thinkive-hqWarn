package com.thinkive.market.bean;

import com.thinkive.base.util.ConvertHelper;

/**
 * ����: ��Ʊ���ͷ��� ��Ȩ: Copyright (c) 2007 ��˾: ˼�ϿƼ� ����: ����� �汾: 1.0 ��������: 2009-2-21
 * ����ʱ��: 22:42:02
 */
public final class StockHelper
{
    
    public final static int SZ_A_STK     = (0);   // ����A
                                         
    public final static int SZ_B_STK     = (1);   // ����B
                                         
    public final static int SZ_ZXB_STK   = (2);   // ������С��
                                         
    public final static int SZ_KJ_STK    = (3);   // ���ڿ���ʽ����
                                         
    public final static int SZ_FJ_STK    = (4);   // ���ڷ��ʽ����
                                         
    public final static int SZ_QZ_STK    = (5);   // ����Ȩ֤
                                         
    public final static int SZ_ZQ_STK    = (6);   // ����ծȯ
                                         
    public final static int SZ_ZS_STK    = (7);   // ����ָ��
                                         
    public final static int SZ_OTHER_STK = (8);   //��֤����
                                         
    public final static int SH_A_STK     = (9);   // �Ϻ�A
                                         
    public final static int SH_B_STK     = (10);  // �Ϻ�B
                                         
    public final static int SH_KJ_STK    = (11);  // �Ϻ�����ʽ����
                                         
    public final static int SH_FJ_STK    = (12);  // �Ϻ����ʽ����
                                         
    public final static int SH_QZ_STK    = (13);  // �Ϻ�Ȩ֤
                                         
    public final static int SH_ZQ_STK    = (14);  // �Ϻ�ծȯ
                                         
    public final static int SH_ZS_STK    = (15);  // �Ϻ�ָ��
                                         
    public final static int SH_OTHER_STK = (16);  // �Ϻ�����
                                         
    public final static int SANBAN_STK   = (17);  // ����
                                         
    public final static int CHUANGYE_STK = (18);  // ��ҵ��
                                         
    public final static int UNKNOWN_STK  = ( -1); // ������Ĺ�Ʊ����
                                         
    public final static int SZ_LOF_STK   = (19);  // ����LOF���� XIONGPAN 2014-10-13
                                         
    public final static int SH_JYF_STK   = (20);  // �Ͻ��������ͻ���
                                         
    public final static int SZ_GZ_STK    = (21);  // ���� ��ծ
                                         
    public final static int SZ_QiZ_STK   = (22);  // ���� ��ծ
                                         
    public final static int SZ_KZZ_STK   = (23);  // ���� ��תծ
                                         
    public final static int SZ_HG_STK    = (24);  // ���� �ع�
                                         
    public final static int SH_GZ_STK    = (25);  // �Ϻ� ��ծ
                                         
    public final static int SH_QiZ_STK   = (26);  // �Ϻ� ��ծ
                                         
    public final static int SH_KZZ_STK   = (27);  // �Ϻ� ��תծ
                                         
    public final static int SH_HG_STK    = (30);  // �Ϻ� �ع�
                                         
    public final static int HK_STK       = 99;    // �۹�ͨ
                                         
    public final static int SZ_QUIT_STK  = (64);  // ��������
                                         
    public final static int SH_QUIT_STK  = (65);  // �Ϻ�����
                                         
    public final static int SH_WARN_STK  = (66);  // �Ϻ����վ�ʾ
                                         
    /**
     * �жϵ�ǰ��Ʊ�����ǲ��������г�ָ������
     * 
     * @param stockCode
     * @return
     */
    private static boolean isSZIndex(String stockCode)
    {
        if ( stockCode.startsWith("39") )
            return true;
        return false;
    }
    
    /**
     * �жϵ�ǰ��Ʊ�����ǲ����Ϻ��г�ָ������
     * 
     * @param stockCode
     * @return
     */
    private static boolean isSHIndex(String stockCode)
    {
        
        int code = ConvertHelper.strToInt(stockCode);
        if ( code < 1000 || code > 999000 ) // �Ϻ�ָ����ɷ�ָ��
            return true;
            
        return false;
    }
    
    /**
     * �ж��Ƿ��ǹ�Ʊ
     * 
     * @param type
     * @return
     */
    public static boolean isGP(int type)
    {
        if ( type == SH_A_STK || type == SH_B_STK || type == SZ_A_STK || type == SZ_B_STK || type == SZ_ZXB_STK
                || type == CHUANGYE_STK || type == SZ_QUIT_STK || type == SH_QUIT_STK || type == SH_WARN_STK )
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * �ж��Ƿ��ǻ���
     * 
     * @param type
     * @return
     */
    public final static boolean isJJ(int type)
    {
        if ( type == SH_KJ_STK || type == SH_FJ_STK || type == SZ_KJ_STK || type == SZ_FJ_STK || type == SH_JYF_STK
                || type == SZ_LOF_STK )
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * �ж��Ƿ���ծȯ
     * 
     * @param type
     * @return
     */
    public final static boolean isZQ(int type)
    {
        if ( type == SH_ZQ_STK || type == SZ_ZQ_STK )
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * �ж��Ƿ���Ȩ֤
     * 
     * @param type
     * @return
     */
    public final static boolean isQZ(int type)
    {
        if ( type == SH_QZ_STK || type == SZ_QZ_STK )
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * �ж��Ƿ���ָ��
     * 
     * @param type
     * @return
     */
    public final static boolean isZS(int type)
    {
        if ( type == SH_ZS_STK || type == SZ_ZS_STK )
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * �ж��Ƿ�������
     * 
     * @param type
     * @return
     */
    public final static boolean isSB(int type)
    {
        return (type == SANBAN_STK);
    }
}
