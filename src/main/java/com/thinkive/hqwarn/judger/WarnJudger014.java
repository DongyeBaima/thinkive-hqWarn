package com.thinkive.hqwarn.judger;

import com.thinkive.base.util.MyLog;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * 打开跌停
 * 描述：打开跌停板，股价从跌停到>=跌停，且买一挂单量不为0
 * @author 何洋
 * @created 2016年7月5日 下午5:56:01
 * @since
 */
public class WarnJudger014 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",打开跌停!";
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
        //从跌停到>=跌停  && 买一挂单量不为0
        if ( stock.getNow() >= stock.getLimitDown() && stock.getBuyvol1() > 0 )
        {
            return true;
        }
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 14;
    }
    
}
