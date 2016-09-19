package com.thinkive.hqwarn.judger;

import java.util.List;

import com.thinkive.hq.bean.RealTimeData;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * 高台跳水
 * 描述：股价上涨超过5%后，3分钟内出现跌幅达4%以上，且这3分钟内每分钟下跌>=0.5%
 * @author 何洋
 * @created 2016年6月29日 下午2:29:43
 * @since
 */
public class WarnJudger006 extends WarnJudger
{
    private final int HIS_RANGE_MINUTE = 3; // 触发器需要缓存的历史分钟数
                                            
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",高台跳水";
    }
    
    @Override
    protected double setTriggervalue(Stock stock)
    {
        return 0;
    }
    
    @Override
    protected int setTrggerRangeType()
    {
        return TRGGER_RANGE_TYPE_DOWN;
    }
    
    @Override
    protected boolean isTrigger(Stock stock)
    {
        //获取分钟节点数据
        List<Integer> nodeIndexList = stock.getUppercentHisData().getMinuteNodeIndexList();
        //获取历史行情数据
        List<RealTimeData> hisDataList = stock.getUppercentHisData().getHisDataList();
        
        //判断节点是否缓存数据是否小于3分钟
        if ( nodeIndexList.size() < HIS_RANGE_MINUTE + 1 )
        {
            return false;
        }
        
        //判断3分钟前的股价涨幅是否大于5%
        int index = nodeIndexList.get(HIS_RANGE_MINUTE);
        if ( hisDataList.get(index).getData() <= 5 )
        {
            return false;
        }
        
        //判断在3分钟内是否每分钟下跌0.5
        float beforeHisRangeMinuteUppercent = hisDataList.get(nodeIndexList.get(0)).getData();//获取历史行情数据的的最新数据的涨跌幅
        float historyUpper = 0;
        for (int i = 1; i <= HIS_RANGE_MINUTE; i++)
        {
            index = nodeIndexList.get(i);//获取nodeIndexList中的第二个分钟过去的节点索引
            
            historyUpper = hisDataList.get(index).getData();//根据索引获取最新数据的前一分钟的涨跌幅
            
            if ( (historyUpper - beforeHisRangeMinuteUppercent) > 0.5 )//如果(前一个  - 后一个)[表示跌幅]分钟节点的跌幅超过0.5
            {
                beforeHisRangeMinuteUppercent = historyUpper;
            }
            else
            {
                return false;
            }
        }
        /**
         * 3min内出现涨幅超4% 当3min前涨跌幅>5%， 且每分钟涨跌幅跌0.5%的前提下
         * 循环3min中涨跌幅数据，用    由于是跌幅  所以反过来减[3min前涨跌幅数据   -  3min之后实时涨跌幅数据]   如果跌超过4%则成立 
         */
        //判断3分钟内是否有跌幅超过4%的  [业务确认相差4%]
        float realTimeData = 0f;//3min内实时涨跌幅数据
        float beforeThreeData = hisDataList.get(0).getData();//3min前涨跌幅数据
        for (int i = 0, len = hisDataList.size(); i < len; i++)
        {
            realTimeData = hisDataList.get(i).getData();
            
            if ( (beforeThreeData - realTimeData) > 4 )//那每一个涨跌幅数据进行比较;最开始为5%，跌幅超过4%即< 1%
            {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        return 6;
    }
}
