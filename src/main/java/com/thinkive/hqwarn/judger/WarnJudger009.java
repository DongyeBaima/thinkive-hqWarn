package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * 大笔买入
 * 描述：出现换手率大于0.1%或大于1000万元的外盘成交
 * @author 何洋
 * @created 2016年7月5日 下午4:59:57
 * @since
 */
public class WarnJudger009 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",大笔买入!";
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
        //System.out.println(stock.getStockCode()+"___"+stock.getTurnoverRate());
        
        //判断换手率>0.1%或者外盘成交金额>1000万
        if ( stock.getTurnoverRate() > 0.1 || stock.getBuyDealMoney() > 10000000 )
        {
            return true;
        }
        
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 9;
    }
}
