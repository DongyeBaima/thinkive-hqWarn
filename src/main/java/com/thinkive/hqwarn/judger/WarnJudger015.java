package com.thinkive.hqwarn.judger;

import com.thinkive.base.util.DateUtil;
import com.thinkive.base.util.MyLog;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.market.bean.StockHelper;

/**
 * 有大卖盘
 * 描述：5档卖盘合计大于500万股或和流通盘的比值大于1%==>5档卖盘合计大于5万手或和流通盘*100的比值大于0.01
 * @author 何洋
 * @created 2016年7月5日 下午5:58:39
 * <p>
 *      我们获得5档单位为"手"、获得流通股单位为"万"
 * </p>
 */
public class WarnJudger015 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "当前价格" + stock.getNow() + ",涨跌幅" + stock.getUppercent()
                + ",大卖盘出现!";
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
        
        //5档卖盘合计大于500万股[100股 == 1手  | 5000000股  == 50000手]  || 5档卖盘/流通股>1%
        float fiveSellCount = stock.getSellvol1() + stock.getSellvol2() + stock.getSellvol3() + stock.getSellvol4()
                + stock.getSellvol5();//计算5档卖盘量
        //500万股  这里单位按照股计算[500 0000]  || stock.getLtag()[单位：万]
        //        if ( "SH603861".equals(stock.getStockCode()) )
        //        {
        //            System.out.println("======>"+DateUtil.FormateDate(stock.getSourceTime()));
        //            System.out.println("股票代码：" + stock.getStockCode() + "\r比值：" + fiveSellCount / stock.getLtag() + "\r五档卖盘量："
        //                    + fiveSellCount + "\r流通股：" + stock.getLtag());
        //        }
        //        MyLog.appLog.warn(stock.getStockCode() + "股票类型：==>" + stock.getStktype() + "\r比值：" + fiveSellCount
        //                / stock.getLtag() + "\r五档卖盘量：" + fiveSellCount + "\r流通股：" + stock.getLtag() + "==>"
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
