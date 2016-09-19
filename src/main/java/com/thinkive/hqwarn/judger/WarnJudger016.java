package com.thinkive.hqwarn.judger;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.market.bean.StockHelper;

/**
 * �д�����
 * ������5�����̺ϼƴ���500��ɻ����ͨ�̵ı�ֵ����1%
 * @author ����
 * @created 2016��7��5�� ����5:58:39
 * @since
 */
public class WarnJudger016 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "��ǰ�۸�" + stock.getNow() + ",�ǵ���" + stock.getUppercent()
                + ",�����̳���!";
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
         * �����ָ��ֱ�ӷ���false
         */
        if ( StockHelper.isZS(stock.getStktype()) )
            return false;
        
        //5�����̺ϼƴ���500���[100��==1��]  || 5������/��ͨ��>1%
        float fiveBuyCount = stock.getBuyvol1() + stock.getBuyvol2() + stock.getBuyvol3() + stock.getBuyvol4()
                + stock.getBuyvol5();//����5��������
        //500���  ���ﵥλ���չɼ���
        if ( fiveBuyCount > 50000 || fiveBuyCount / stock.getLtag() * 100 > 0.01 )//[��*100/���>0.01]
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
