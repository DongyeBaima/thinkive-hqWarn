package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
/**
 * 封跌停板
 * 描述：股价由非跌停价到跌停板且买一挂单量为0 
 * @author 何洋
 * @created 2016年7月5日 下午5:40:27
 * @since
 */
public class WarnJudger012 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",封跌停板!";
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
        //非跌停价到涨停板==>现价=跌停价
        if ( stock.getNow() == stock.getLimitDown() && stock.getBuyvol1() == 0 )
        {
            return true;
        }
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 12;
    }
    
}
