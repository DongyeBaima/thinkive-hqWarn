package com.thinkive.hqwarn.judger;

import com.thinkive.base.util.DateUtil;
import com.thinkive.base.util.MyLog;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.market.bean.StockHelper;

/**
 * �д�����
 * ������5�����̺ϼƴ���500��ɻ����ͨ�̵ı�ֵ����1%==>5�����̺ϼƴ���5���ֻ����ͨ��*100�ı�ֵ����0.01
 * @author ����
 * @created 2016��7��5�� ����5:58:39
 * <p>
 *      ���ǻ��5����λΪ"��"�������ͨ�ɵ�λΪ"��"
 * </p>
 */
public class WarnJudger015 extends WarnJudger
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
        
        //5�����̺ϼƴ���500���[100�� == 1��  | 5000000��  == 50000��]  || 5������/��ͨ��>1%
        float fiveSellCount = stock.getSellvol1() + stock.getSellvol2() + stock.getSellvol3() + stock.getSellvol4()
                + stock.getSellvol5();//����5��������
        //500���  ���ﵥλ���չɼ���[500 0000]  || stock.getLtag()[��λ����]
        //        if ( "SH603861".equals(stock.getStockCode()) )
        //        {
        //            System.out.println("======>"+DateUtil.FormateDate(stock.getSourceTime()));
        //            System.out.println("��Ʊ���룺" + stock.getStockCode() + "\r��ֵ��" + fiveSellCount / stock.getLtag() + "\r�嵵��������"
        //                    + fiveSellCount + "\r��ͨ�ɣ�" + stock.getLtag());
        //        }
        //        MyLog.appLog.warn(stock.getStockCode() + "��Ʊ���ͣ�==>" + stock.getStktype() + "\r��ֵ��" + fiveSellCount
        //                / stock.getLtag() + "\r�嵵��������" + fiveSellCount + "\r��ͨ�ɣ�" + stock.getLtag() + "==>"
        //                + (fiveSellCount / stock.getLtag() > 0.01));
        if ( fiveSellCount > 50000 || fiveSellCount / stock.getLtag() * 100 > 0.01 )
        {
            return true;
        }
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 15;
    }
}
