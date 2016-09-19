package com.thinkive.hqwarn.judger;

import java.util.List;

import com.thinkive.hq.bean.RealTimeData;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * 快速反弹
 * 描述：指股价下跌超过5%，且其后的3分钟内出现涨幅>=3%
 * @author 何洋
 * @created 2016年6月30日 下午1:20:12
 * @since
 */
public class WarnJudger007 extends WarnJudger
{
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",快速反弹!";
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
        //获取3min历史行情数据
        List<RealTimeData> hisDataList = stock.getUppercentHisData().getHisDataList();
        
        //判断当期时间的涨幅是否超过3%
        if ( hisDataList.get(nodeIndexList.get(0)).getData() < 3 )
        {
            return false;
        }
        
        //判断3min中前到当前时间是否有跌超过5%的
        int len = hisDataList.size();
        for (int i = 0; i < len; i++)
        {
            //判断3min中内的涨跌幅是否跌超过5%
            if ( hisDataList.get(i).getData() < -5 )
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
        return 7;
    }
}
