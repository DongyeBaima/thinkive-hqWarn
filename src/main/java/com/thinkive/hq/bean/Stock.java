package com.thinkive.hq.bean;

import com.thinkive.base.util.DateUtil;

/**
 * 描述: 版权: Copyright (c) 2007 公司: 思迪科技 作者: 易庆锋 版本: 1.0 创建日期: 2009-1-12 创建时间:
 * 14:48:51
 */
public class Stock
{
    private short        serno;                                 // 股票序号码
                         
    private long         sourceTime;                            // 行情源时间
                         
    private String       code;                                  // 股票代码
                         
    private String       name;                                  // 股票名称
                         
    private int          stktype          = -1;                 // 股票类型
                                          
    private float        yesterday;                             // 昨日收盘价
                         
    private float        open;                                  // 今日开盘价
                         
    private float        high;                                  // 最高价
                         
    private float        low;                                   // 最低价
                         
    private float        now;                                   // 现价
                         
    private float        buyprice1;                             // 买一
                         
    private float        buyprice2;                             // 买二
                         
    private float        buyprice3;                             // 买三
                         
    private float        buyprice4;                             // 买四
                         
    private float        buyprice5;                             // 买五
                         
    private float        buyvol1;                               // 买盘量一
                         
    private float        buyvol2;                               // 买盘量二
                         
    private float        buyvol3;                               // 买盘量三
                         
    private float        buyvol4;                               // 买盘量四
                         
    private float        buyvol5;                               // 买盘量五
                         
    private float        sellprice1;                            // 卖一
                         
    private float        sellprice2;                            // 卖二
                         
    private float        sellprice3;                            // 卖三
                         
    private float        sellprice4;                            // 卖四
                         
    private float        sellprice5;                            // 卖五
                         
    private float        sellvol1;                              // 卖盘量一
                         
    private float        sellvol2;                              // 卖盘量二
                         
    private float        sellvol3;                              // 卖盘量三
                         
    private float        sellvol4;                              // 卖盘量四
                         
    private float        sellvol5;                              // 卖盘量五
                         
    private float        volume;                                // 总成交量
                         
    private double       amount;                                // 总成交金额
                         
    private float        thedeal;                               // 现手
                         
    private float        inside;                                // 卖盘
                         
    private float        outside;                               // 买盘
                         
    private short        dealno;                                // 成交明细数量
                         
    private float        min5change;                            // 5分钟涨跌幅
                         
    private int          curdate;                               // 当前日期
                         
    private short        minutes;                               // 分钟数
                         
    private float        volrate;                               // 量比
                         
    private String       market;                                // 市场(大写SZ or SH)
                         
    private float        up               = 0.0F;               // 涨幅
                                          
    private float        uppercent        = 0.0F;               // 涨幅百分比
                                          
    private StockHisData uppercentHisData = new StockHisData(); // 历史涨跌幅
                                          
    // 新增加排序字段
    private float        flux             = 0.0F;               // 振幅
                                          
    private int          WC;                                    // 委差
                         
    private float        WB               = 0.0F;               // 委比
                                          
    private float        average;                               // 均价
                         
    private float        limitUp          = 0.0F;               // 涨停价
                                          
    private float        limitDown        = 0.0F;               // 跌停价
                                          
    private int          suspendMark;                           // 停牌标志 1停牌 2 非停牌
                         
    private String       transferType;                          // 转让类型【新三板字段】
                         
    private String       transferState;                         // 转让状态【新三板字段】
                         
    private int          marginTradMark;                        // 融资融券标识 （0:无 ， 1:融资 ， 2:融券 ， 3:融资融券）
                         
    private String       stockCode;
                         
    private float        sellDealMoney;                         // 内盘成交金额
                         
    private float        buyDealMoney;                          // 外盘成交金额
                         
    private float        turnoverRate;                          // 换手率
                         
    private float        ltag;                                  // 流通A股
                         
    public float getSellDealMoney()
    {
        return sellDealMoney;
    }
    
    public float getBuyDealMoney()
    {
        return buyDealMoney;
    }
    
    public float getTurnoverRate()
    {
        return turnoverRate;
    }
    
    public float getLtag()
    {
        return ltag;
    }
    
    public void setSellDealMoney(float sellDealMoney)
    {
        this.sellDealMoney = sellDealMoney;
    }
    
    public void setBuyDealMoney(float buyDealMoney)
    {
        this.buyDealMoney = buyDealMoney;
    }
    
    public void setTurnoverRate(float turnoverRate)
    {
        this.turnoverRate = turnoverRate;
    }
    
    public void setLtag(float ltag)
    {
        this.ltag = ltag;
    }
    
    public short getSerno()
    {
        return serno;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public int getStktype()
    {
        return stktype;
    }
    
    public float getYesterday()
    {
        return yesterday;
    }
    
    public float getOpen()
    {
        return open;
    }
    
    public float getHigh()
    {
        return high;
    }
    
    public float getLow()
    {
        return low;
    }
    
    public float getNow()
    {
        return now;
    }
    
    public float getBuyprice1()
    {
        return buyprice1;
    }
    
    public StockHisData getUppercentHisData()
    {
        return uppercentHisData;
    }
    
    public void setUppercentHisData(StockHisData uppercentHisData)
    {
        this.uppercentHisData = uppercentHisData;
    }
    
    public float getBuyprice2()
    {
        return buyprice2;
    }
    
    public float getBuyprice3()
    {
        return buyprice3;
    }
    
    public float getBuyprice4()
    {
        return buyprice4;
    }
    
    public float getBuyprice5()
    {
        return buyprice5;
    }
    
    public float getBuyvol1()
    {
        return buyvol1;
    }
    
    public float getBuyvol2()
    {
        return buyvol2;
    }
    
    public float getBuyvol3()
    {
        return buyvol3;
    }
    
    public float getBuyvol4()
    {
        return buyvol4;
    }
    
    public float getBuyvol5()
    {
        return buyvol5;
    }
    
    public float getSellprice1()
    {
        return sellprice1;
    }
    
    public float getSellprice2()
    {
        return sellprice2;
    }
    
    public float getSellprice3()
    {
        return sellprice3;
    }
    
    public float getSellprice4()
    {
        return sellprice4;
    }
    
    public float getSellprice5()
    {
        return sellprice5;
    }
    
    public float getSellvol1()
    {
        return sellvol1;
    }
    
    public float getSellvol2()
    {
        return sellvol2;
    }
    
    public float getSellvol3()
    {
        return sellvol3;
    }
    
    public float getSellvol4()
    {
        return sellvol4;
    }
    
    public float getSellvol5()
    {
        return sellvol5;
    }
    
    public float getVolume()
    {
        return volume;
    }
    
    public double getAmount()
    {
        return amount;
    }
    
    public float getThedeal()
    {
        return thedeal;
    }
    
    public float getInside()
    {
        return inside;
    }
    
    public float getOutside()
    {
        return outside;
    }
    
    public short getDealno()
    {
        return dealno;
    }
    
    public float getMin5change()
    {
        return min5change;
    }
    
    public int getCurdate()
    {
        return curdate;
    }
    
    public short getMinutes()
    {
        return minutes;
    }
    
    public String getMarket()
    {
        return market;
    }
    
    public float getUp()
    {
        return up;
    }
    
    public float getUppercent()
    {
        return uppercent;
    }
    
    public float getFlux()
    {
        return flux;
    }
    
    public int getWC()
    {
        return WC;
    }
    
    public float getWB()
    {
        return WB;
    }
    
    public float getAverage()
    {
        return average;
    }
    
    public float getLimitUp()
    {
        return limitUp;
    }
    
    public float getLimitDown()
    {
        return limitDown;
    }
    
    public int getSuspendMark()
    {
        return suspendMark;
    }
    
    public String getTransferType()
    {
        return transferType;
    }
    
    public String getTransferState()
    {
        return transferState;
    }
    
    public int getMarginTradMark()
    {
        return marginTradMark;
    }
    
    public String getStockCode()
    {
        return stockCode;
    }
    
    public void setSerno(short serno)
    {
        this.serno = serno;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public void setStktype(int stktype)
    {
        this.stktype = stktype;
    }
    
    public void setYesterday(float yesterday)
    {
        this.yesterday = yesterday;
    }
    
    public void setOpen(float open)
    {
        this.open = open;
    }
    
    public void setHigh(float high)
    {
        this.high = high;
    }
    
    public void setLow(float low)
    {
        this.low = low;
    }
    
    public void setNow(float now)
    {
        this.now = now;
    }
    
    public void setBuyprice1(float buyprice1)
    {
        this.buyprice1 = buyprice1;
    }
    
    public void setBuyprice2(float buyprice2)
    {
        this.buyprice2 = buyprice2;
    }
    
    public void setBuyprice3(float buyprice3)
    {
        this.buyprice3 = buyprice3;
    }
    
    public void setBuyprice4(float buyprice4)
    {
        this.buyprice4 = buyprice4;
    }
    
    public void setBuyprice5(float buyprice5)
    {
        this.buyprice5 = buyprice5;
    }
    
    public void setBuyvol1(float buyvol1)
    {
        this.buyvol1 = buyvol1;
    }
    
    public void setBuyvol2(float buyvol2)
    {
        this.buyvol2 = buyvol2;
    }
    
    public void setBuyvol3(float buyvol3)
    {
        this.buyvol3 = buyvol3;
    }
    
    public void setBuyvol4(float buyvol4)
    {
        this.buyvol4 = buyvol4;
    }
    
    public void setBuyvol5(float buyvol5)
    {
        this.buyvol5 = buyvol5;
    }
    
    public void setSellprice1(float sellprice1)
    {
        this.sellprice1 = sellprice1;
    }
    
    public void setSellprice2(float sellprice2)
    {
        this.sellprice2 = sellprice2;
    }
    
    public void setSellprice3(float sellprice3)
    {
        this.sellprice3 = sellprice3;
    }
    
    public void setSellprice4(float sellprice4)
    {
        this.sellprice4 = sellprice4;
    }
    
    public void setSellprice5(float sellprice5)
    {
        this.sellprice5 = sellprice5;
    }
    
    public void setSellvol1(float sellvol1)
    {
        this.sellvol1 = sellvol1;
    }
    
    public void setSellvol2(float sellvol2)
    {
        this.sellvol2 = sellvol2;
    }
    
    public void setSellvol3(float sellvol3)
    {
        this.sellvol3 = sellvol3;
    }
    
    public void setSellvol4(float sellvol4)
    {
        this.sellvol4 = sellvol4;
    }
    
    public void setSellvol5(float sellvol5)
    {
        this.sellvol5 = sellvol5;
    }
    
    public void setVolume(float volume)
    {
        this.volume = volume;
    }
    
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    
    public void setThedeal(float thedeal)
    {
        this.thedeal = thedeal;
    }
    
    public void setInside(float inside)
    {
        this.inside = inside;
    }
    
    public void setOutside(float outside)
    {
        this.outside = outside;
    }
    
    public void setDealno(short dealno)
    {
        this.dealno = dealno;
    }
    
    public void setMin5change(float min5change)
    {
        this.min5change = min5change;
    }
    
    public void setCurdate(int curdate)
    {
        this.curdate = curdate;
    }
    
    public void setMinutes(short minutes)
    {
        this.minutes = minutes;
    }
    
    public void setMarket(String market)
    {
        this.market = market;
    }
    
    public void setUp(float up)
    {
        this.up = up;
    }
    
    public void setUppercent(float uppercent)
    {
        this.uppercent = uppercent;
        
        uppercentHisData.addData(sourceTime, uppercent);
        
    }
    
    public void setFlux(float flux)
    {
        this.flux = flux;
    }
    
    public void setWC(int wC)
    {
        WC = wC;
    }
    
    public void setWB(float wB)
    {
        WB = wB;
    }
    
    public void setAverage(float average)
    {
        this.average = average;
    }
    
    public void setLimitUp(float limitUp)
    {
        this.limitUp = limitUp;
    }
    
    public void setLimitDown(float limitDown)
    {
        this.limitDown = limitDown;
    }
    
    public void setSuspendMark(int suspendMark)
    {
        this.suspendMark = suspendMark;
    }
    
    public void setTransferType(String transferType)
    {
        this.transferType = transferType;
    }
    
    public void setTransferState(String transferState)
    {
        this.transferState = transferState;
    }
    
    public void setMarginTradMark(int marginTradMark)
    {
        this.marginTradMark = marginTradMark;
    }
    
    public void setStockCode(String stockCode)
    {
        this.stockCode = stockCode;
    }
    
    public float getVolrate()
    {
        return volrate;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setVolrate(float volrate)
    {
        this.volrate = volrate;
    }
    
    public long getSourceTime()
    {
        return sourceTime;
    }
    
    public void setSourceTime(long sourceTime)
    {
        this.sourceTime = sourceTime;
    }
    
    @Override
    public String toString()
    {
        return "Stock [name=" + name + ", stockCode=" + stockCode + ", yesterday=" + yesterday + ", open=" + open
                + ", high=" + high + ", low=" + low + ", now=" + now + ", uppercent=" + uppercent + ", sourceTime="
                + DateUtil.FormateDate(sourceTime) + "]";
    }
    
}
