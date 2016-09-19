package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.JudgerHelper;

/**
 * @����: �ּ����������ж�
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��12�� ����3:33:35
 */
public class WarnJudger002 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        String now = JudgerHelper.formatStockPrice(stock);
        
        return "��ǰ�ɼ��Ѵﵽ" + now;
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
