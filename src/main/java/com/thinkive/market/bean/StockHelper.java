package com.thinkive.market.bean;

import com.thinkive.base.util.ConvertHelper;

/**
 * 描述: 股票类型分类 版权: Copyright (c) 2007 公司: 思迪科技 作者: 易庆锋 版本: 1.0 创建日期: 2009-2-21
 * 创建时间: 22:42:02
 */
public final class StockHelper
{
    
    public final static int SZ_A_STK     = (0);   // 深圳A
                                         
    public final static int SZ_B_STK     = (1);   // 深圳B
                                         
    public final static int SZ_ZXB_STK   = (2);   // 深圳中小板
                                         
    public final static int SZ_KJ_STK    = (3);   // 深圳开放式基金
                                         
    public final static int SZ_FJ_STK    = (4);   // 深圳封闭式基金
                                         
    public final static int SZ_QZ_STK    = (5);   // 深圳权证
                                         
    public final static int SZ_ZQ_STK    = (6);   // 深圳债券
                                         
    public final static int SZ_ZS_STK    = (7);   // 深圳指数
                                         
    public final static int SZ_OTHER_STK = (8);   //深证其他
                                         
    public final static int SH_A_STK     = (9);   // 上海A
                                         
    public final static int SH_B_STK     = (10);  // 上海B
                                         
    public final static int SH_KJ_STK    = (11);  // 上海开放式基金
                                         
    public final static int SH_FJ_STK    = (12);  // 上海封闭式基金
                                         
    public final static int SH_QZ_STK    = (13);  // 上海权证
                                         
    public final static int SH_ZQ_STK    = (14);  // 上海债券
                                         
    public final static int SH_ZS_STK    = (15);  // 上海指数
                                         
    public final static int SH_OTHER_STK = (16);  // 上海其他
                                         
    public final static int SANBAN_STK   = (17);  // 三板
                                         
    public final static int CHUANGYE_STK = (18);  // 创业版
                                         
    public final static int UNKNOWN_STK  = ( -1); // 不清楚的股票代码
                                         
    public final static int SZ_LOF_STK   = (19);  // 深圳LOF基金 XIONGPAN 2014-10-13
                                         
    public final static int SH_JYF_STK   = (20);  // 上交所交易型基金
                                         
    public final static int SZ_GZ_STK    = (21);  // 深圳 国债
                                         
    public final static int SZ_QiZ_STK   = (22);  // 深圳 企债
                                         
    public final static int SZ_KZZ_STK   = (23);  // 深圳 可转债
                                         
    public final static int SZ_HG_STK    = (24);  // 深圳 回购
                                         
    public final static int SH_GZ_STK    = (25);  // 上海 国债
                                         
    public final static int SH_QiZ_STK   = (26);  // 上海 企债
                                         
    public final static int SH_KZZ_STK   = (27);  // 上海 可转债
                                         
    public final static int SH_HG_STK    = (30);  // 上海 回购
                                         
    public final static int HK_STK       = 99;    // 港股通
                                         
    public final static int SZ_QUIT_STK  = (64);  // 深圳退市
                                         
    public final static int SH_QUIT_STK  = (65);  // 上海退市
                                         
    public final static int SH_WARN_STK  = (66);  // 上海风险警示
                                         
    /**
     * 判断当前股票代码是不是深圳市场指数代码
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
     * 判断当前股票代码是不是上海市场指数代码
     * 
     * @param stockCode
     * @return
     */
    private static boolean isSHIndex(String stockCode)
    {
        
        int code = ConvertHelper.strToInt(stockCode);
        if ( code < 1000 || code > 999000 ) // 上海指数或成份指数
            return true;
            
        return false;
    }
    
    /**
     * 判断是否是股票
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
     * 判断是否是基金
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
     * 判断是否是债券
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
     * 判断是否是权证
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
     * 判断是否是指数
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
     * 判断是否是三板
     * 
     * @param type
     * @return
     */
    public final static boolean isSB(int type)
    {
        return (type == SANBAN_STK);
    }
}
