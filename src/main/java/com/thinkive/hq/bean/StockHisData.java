package com.thinkive.hq.bean;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.thinkive.hqwarn.util.HQHelper;
import com.thinkive.hqwarn.util.ServerConfig;

/**
 * @描述: 股票历史涨跌幅缓存
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月18日 下午4:26:57
 */
public class StockHisData
{
    private static long        historyTimeRange = ServerConfig.HQ_HISTORY_RETAIN_MINUTE * 60 * 1000 + 10000;
                                                
    private List<RealTimeData> hisDataList      = new LinkedList<>();
                                                
    private List<Integer>      minuteNodeIndexList;
                               
    public void addData(RealTimeData data)
    {
        hisDataList.add(data);
        
        removeOverdueData(data);
        
        minuteNodeIndexList = HQHelper.findNodeValue(hisDataList);
    }
    
    public void addData(long time, float data)
    {
        RealTimeData rData = new RealTimeData();
        
        rData.setTime(time);
        rData.setData(data);
        
        addData(rData);
        
    }
    
    /**
     * @描述: 删除过期数据
     * @作者: 王嵊俊
     * @创建日期: 2016年6月22日 下午4:00:34
     */
    public void removeOverdueData(RealTimeData newData)
    {
        long rTime = newData.getTime();
        long hTime = 0;
        
        Iterator<RealTimeData> iterator = hisDataList.iterator();
        RealTimeData hData = null;
        while (iterator.hasNext())
        {
            hData = iterator.next();
            
            hTime = hData.getTime();
            
            if ( (rTime - hTime) > historyTimeRange )
            {
                hData.setValid(false);
                iterator.remove();
                
            }
            else
            {
                break;
            }
        }
    }
    
    public boolean isEmpty()
    {
        return hisDataList.isEmpty();
    }
    
    public int size()
    {
        return hisDataList.size();
    }
    
    public List<RealTimeData> getHisDataList()
    {
        return hisDataList;
    }
    
    public void setHisDataList(List<RealTimeData> hisDataList)
    {
        this.hisDataList = hisDataList;
    }
    
    public List<Integer> getMinuteNodeIndexList()
    {
        return minuteNodeIndexList;
    }
    
    public void setMinuteNodeIndexList(List<Integer> minuteNodeIndexList)
    {
        this.minuteNodeIndexList = minuteNodeIndexList;
    }
    
}
