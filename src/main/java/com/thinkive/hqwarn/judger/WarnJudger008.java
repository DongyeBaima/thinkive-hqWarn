package com.thinkive.hqwarn.judger;

import java.util.List;

import com.thinkive.hq.bean.RealTimeData;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * 指股价下跌超过2%，且其后的3分钟内出现跌幅>=3%
 * 描述：
 * @author 何洋
 * @created 2016年7月4日 下午3:19:59
 * @since
 */
public class WarnJudger008 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",加速下跌!";
    }
    
    @Override
    protected double setTriggervalue(Stock stock)
    {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    protected int setTrggerRangeType()
    {
        // TODO Auto-generated method stub
        return TRGGER_RANGE_TYPE_DOWN;
    }
    
    @Override
    protected boolean isTrigger(Stock stock)
    {
        //获取3min节点数据的索引
        List<Integer> nodeIndexList = stock.getUppercentHisData().getMinuteNodeIndexList();
        //获取历史数据列表
        List<RealTimeData> hisDataList = stock.getUppercentHisData().getHisDataList();
        
        //首先判断3min后的的跌幅是否超过3%[其后的3分钟内出现跌幅>=3%]
        if ( hisDataList.get(nodeIndexList.get(0)).getData() >= -3 )
        {
            return false;
        }
        
        //从当前时间往前推3min，判断是否有跌幅超过2%的[首先判断当前股价是否下跌超过2%]
        for (int i = 0; i < hisDataList.size(); i++)
        {
            //判断3min中前的涨跌幅是否跌超过5%
            if ( hisDataList.get(i).getData() < -2 )
            {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 8;
    }
    
}
