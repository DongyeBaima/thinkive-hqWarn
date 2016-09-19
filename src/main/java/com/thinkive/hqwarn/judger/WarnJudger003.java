package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.JudgerHelper;

/**
 * @����: �ǵ������������ж�
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��12�� ����3:33:35
 */
public class WarnJudger003 extends WarnJudger
{
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 3;
    }
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        String now = JudgerHelper.formatStockPrice(stock);
        String uppercent = JudgerHelper.formatWarnInfoValue(warnInfo);
        
        return "��ǰ�ǵ����Ѵ�" + uppercent + "%";
    }
    
    @Override
    protected double setTriggervalue(Stock stock)
    {
        // TODO Auto-generated method stub
        return stock.getUppercent();
    }
    
    @Override
    protected int setTrggerRangeType()
    {
        // TODO Auto-generated method stub
        return TRGGER_RANGE_TYPE_UP;
    }
    
    @Override
    protected boolean isTrigger(Stock stock)
    {
        // TODO Auto-generated method stub
        return true;
    }
    
}
