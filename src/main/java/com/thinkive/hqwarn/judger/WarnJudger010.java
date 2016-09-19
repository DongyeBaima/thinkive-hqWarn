package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * 大笔卖出
 * 描述：出现换手率大于0.1%或大于1000万元的内盘成交
 * @author 何洋
 * @created 2016年7月5日 下午5:22:03
 * @since
 */
public class WarnJudger010 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",大笔卖出!";
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
        //换手率大于0.1%或大于1000万元的内盘成交[sellDealMoney：内盘成交额字段]
        if ( stock.getTurnoverRate() > 0.1 || stock.getSellDealMoney() > 10000000 )
        {
            return true;
        }
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 10;
    }
    
}
