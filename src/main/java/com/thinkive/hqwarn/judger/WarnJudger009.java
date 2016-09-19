package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * �������
 * ���������ֻ����ʴ���0.1%�����1000��Ԫ�����̳ɽ�
 * @author ����
 * @created 2016��7��5�� ����4:59:57
 * @since
 */
public class WarnJudger009 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "��ǰ�۸�" + stock.getNow() + ",�ǵ���" + stock.getUppercent()
                + ",�������!";
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
        
        //�жϻ�����>0.1%�������̳ɽ����>1000��
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
