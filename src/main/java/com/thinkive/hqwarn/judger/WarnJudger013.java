package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * ����ͣ
 * ����������ͣ�壬�ɼ۴���ͣ��<=��ͣ������һ�ҵ�����Ϊ0
 * @author ����
 * @created 2016��7��5�� ����5:52:24
 * @since
 */
public class WarnJudger013 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "��ǰ�۸�" + stock.getNow() + ",�ǵ���" + stock.getUppercent()
                + ",����ͣ!";
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
        //        �ɼ۴���ͣ��<=��ͣ  ==>�ּ�С����ͣ�� && ��һ�ҵ�����Ϊ0,
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
