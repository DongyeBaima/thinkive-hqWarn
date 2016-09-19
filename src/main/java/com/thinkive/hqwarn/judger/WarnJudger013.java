package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * 打开涨停
 * 描述：打开涨停板，股价从涨停到<=涨停，且卖一挂单量不为0
 * @author 何洋
 * @created 2016年7月5日 下午5:52:24
 * @since
 */
public class WarnJudger013 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",打开涨停!";
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
        //        股价从涨停到<=涨停  ==>现价小于涨停价 && 卖一挂单量不为0,
        if ( stock.getNow() <= stock.getLimitUp() && stock.getSellvol1() > 0 )
        {
            return true;
        }
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 13;
    }
    
}
