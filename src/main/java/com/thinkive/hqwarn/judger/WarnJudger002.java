package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.JudgerHelper;

/**
 * @描述: 现价下限提醒判断
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月12日 下午3:33:35
 */
public class WarnJudger002 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        String now = JudgerHelper.formatStockPrice(stock);
        
        return "当前股价已达到" + now;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 2;
    }
    
    @Override
    protected double setTriggervalue(Stock stock)
    {
        // TODO Auto-generated method stub
        return stock.getNow();
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
        // TODO Auto-generated method stub
        return true;
    }
    
}
