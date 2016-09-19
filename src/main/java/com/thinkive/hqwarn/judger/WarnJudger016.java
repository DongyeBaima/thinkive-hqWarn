package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.market.bean.StockHelper;

/**
 * 有大买盘
 * 描述：5档买盘合计大于500万股或和流通盘的比值大于1%
 * @author 何洋
 * @created 2016年7月5日 下午5:58:39
 * @since
 */
public class WarnJudger016 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",大买盘出现!";
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
        /**
         * 如果是指数直接返回false
         */
        if ( StockHelper.isZS(stock.getStktype()) )
            return false;
        
        //5档买盘合计大于500万股[100股==1手]  || 5档买盘/流通股>1%
        float fiveBuyCount = stock.getBuyvol1() + stock.getBuyvol2() + stock.getBuyvol3() + stock.getBuyvol4()
                + stock.getBuyvol5();//计算5档买盘量
        //500万股  这里单位按照股计算
        if ( fiveBuyCount > 50000 || fiveBuyCount / stock.getLtag() * 100 > 0.01 )//[手*100/万股>0.01]
        {
            return true;
        }
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 16;
    }
    
}
