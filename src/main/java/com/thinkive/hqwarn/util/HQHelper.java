package com.thinkive.hqwarn.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.thinkive.base.util.DateUtil;
import com.thinkive.base.util.MyStringUtil;
import com.thinkive.hq.bean.RealTimeData;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.cache.HQState;
import com.thinkive.hqwarn.cache.ServerCache;

/**
 * @描述: 行情共用方法
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月12日 下午2:14:11
 */
public class HQHelper
{
    private static final Log  logger     = LogFactory.getLog("Server");
                                         
    private static final long ONE_MINUTE = 60 * 1000;
                                         
    /**
     * @描述: 实时行情计算
     * @作者: 王嵊俊
     * @创建日期: 2016年5月12日 下午2:15:31
     * @param stockCode
     */
    public static void calculate(String stockCode)
    {
        Stock stock = ServerCache.getStock(stockCode);
        
        if ( stock == null )
        {
            logger.debug("	--	@实时行情计算	--	该股票不存在，[ StockCode : " + stockCode + "]");
            return;
        }
        
        float yesClose = stock.getYesterday();
        float now = stock.getNow();
        
        if ( yesClose == 0 )
        {
            logger.debug("	--	@实时行情计算	--	股票昨收为0，[ StockCode : " + stockCode + "]");
            return;
        }
        
        // -- -- 计算股票涨跌幅
        
        float up = now - yesClose;
        float uppercent = up / yesClose * 100;
        
        stock.setUp(up);
        stock.setUppercent(uppercent);
        
        // -- -- 计算换手率
        
        float ltag = stock.getLtag();
        float volum = stock.getVolume();
        
        if ( ltag != 0 )
        {
            float turnoverRate = volum / ltag;
            
            stock.setTurnoverRate(turnoverRate);
        }
    }
    
    /**
     * @描述: 找到一分钟节点值
     * @作者: 王嵊俊
     * @创建日期: 2016年6月27日 下午3:53:55
     */
    public static List<Integer> findNodeValue(List<RealTimeData> hisDataList)
    {
        if ( hisDataList.isEmpty() )
        {
            return new ArrayList<>();
        }
        
        RealTimeData data = hisDataList.get(hisDataList.size() - 1);
        
        long stime = data.getTime();
        long etime = hisDataList.get(0).getTime();
        
        long n = (stime - etime) / ONE_MINUTE;
        
        List<Integer> list = new ArrayList<>((int) n);
        
        list.add(hisDataList.size() - 1);
        
        long findTime = 0;
        int index = 0;
        for (long i = 1; i <= n; i++)
        {
            findTime = stime - i * ONE_MINUTE;
            
            index = binarySearch(findTime, hisDataList, 0, hisDataList.size() - 1);
            
            list.add(index);
            
            if ( index == 0 )
            {
                break;
            }
            
        }
        
        return list;
    }
    
    /**
     * @描述: 用二分法 找到key在list中的位置
     * @作者: 王嵊俊
     * @创建日期: 2016年5月10日 上午10:37:44
     * @param warnInfo
     * @param list
     * @param start
     * @param end
     * @return
     */
    public static int binarySearch(long key, List<RealTimeData> list, int start, int end)
    {
        while (start <= end)
        {
            if ( start == end )
            {
                return start;
            }
            
            int mid = (start + end) >> 1;
            
            long midValue = list.get(mid).getTime();
            
            if ( key > midValue )
            {
                start = mid + 1;
            }
            else if ( key < midValue )
            {
                end = mid - 1;
            }
            else
            {
                return mid;
            }
        }
        
        return 0;
    }
    
    /**
     * @描述: 判断此时是否在推送时间内
     * @作者: 王嵊俊
     * @创建日期: 2016年6月15日 下午2:39:46
     * @param nowTime
     * @param pushTime
     * @return
     */
    public static boolean isRightTime(String market)
    {
        long hqSourceTime = HQState.get(market).getSourceTime();
        int[][] pushTime = getPushTime(market);
        
        int minute = MyStringUtil.strToInt(DateUtil.FormateMinute(hqSourceTime));
        
        for (int i = 0, len = pushTime.length; i < len; i++)
        {
            if ( minute >= pushTime[i][0] && minute <= pushTime[i][1] )
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * @描述: 根据市场类型 获取推送时间
     * @作者: 王嵊俊	
     * @创建日期: 2016年6月15日 下午2:50:50
     * @param market
     * @return
     */
    public static int[][] getPushTime(String market)
    {
        if ( ServerConfig.HQ_MARKET_HK.equals(market) )
        {
            return ServerConfig.PUSH_TIME_HK;
        }
        
        return ServerConfig.PUSH_TIME_HS;
    }
    
    /**
     * @描述: 判断 包是否可以接受
     * @作者: 王嵊俊
     * @创建日期: 2016年6月15日 下午3:07:49
     * @param market
     * @return
     */
    public static boolean canReceive(String market)
    {
        
        if ( HQState.get(market).isInit() && isRightTime(market) )
        {
            return true;
        }
        
        return false;
    }
    
}
