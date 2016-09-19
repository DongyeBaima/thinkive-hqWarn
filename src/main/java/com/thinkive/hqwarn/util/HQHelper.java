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
 * @����: ���鹲�÷���
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��12�� ����2:14:11
 */
public class HQHelper
{
    private static final Log  logger     = LogFactory.getLog("Server");
                                         
    private static final long ONE_MINUTE = 60 * 1000;
                                         
    /**
     * @����: ʵʱ�������
     * @����: ���ӿ�
     * @��������: 2016��5��12�� ����2:15:31
     * @param stockCode
     */
    public static void calculate(String stockCode)
    {
        Stock stock = ServerCache.getStock(stockCode);
        
        if ( stock == null )
        {
            logger.debug("	--	@ʵʱ�������	--	�ù�Ʊ�����ڣ�[ StockCode : " + stockCode + "]");
            return;
        }
        
        float yesClose = stock.getYesterday();
        float now = stock.getNow();
        
        if ( yesClose == 0 )
        {
            logger.debug("	--	@ʵʱ�������	--	��Ʊ����Ϊ0��[ StockCode : " + stockCode + "]");
            return;
        }
        
        // -- -- �����Ʊ�ǵ���
        
        float up = now - yesClose;
        float uppercent = up / yesClose * 100;
        
        stock.setUp(up);
        stock.setUppercent(uppercent);
        
        // -- -- ���㻻����
        
        float ltag = stock.getLtag();
        float volum = stock.getVolume();
        
        if ( ltag != 0 )
        {
            float turnoverRate = volum / ltag;
            
            stock.setTurnoverRate(turnoverRate);
        }
    }
    
    /**
     * @����: �ҵ�һ���ӽڵ�ֵ
     * @����: ���ӿ�
     * @��������: 2016��6��27�� ����3:53:55
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
     * @����: �ö��ַ� �ҵ�key��list�е�λ��
     * @����: ���ӿ�
     * @��������: 2016��5��10�� ����10:37:44
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
     * @����: �жϴ�ʱ�Ƿ�������ʱ����
     * @����: ���ӿ�
     * @��������: 2016��6��15�� ����2:39:46
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
     * @����: �����г����� ��ȡ����ʱ��
     * @����: ���ӿ�	
     * @��������: 2016��6��15�� ����2:50:50
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
     * @����: �ж� ���Ƿ���Խ���
     * @����: ���ӿ�
     * @��������: 2016��6��15�� ����3:07:49
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
