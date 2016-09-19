package com.thinkive.hq.bean;

import com.thinkive.base.util.DateUtil;

/**
 * ����: ��Ȩ: Copyright (c) 2007 ��˾: ˼�ϿƼ� ����: ����� �汾: 1.0 ��������: 2009-1-12 ����ʱ��:
 * 14:48:51
 */
public class Stock
{
    private short        serno;                                 // ��Ʊ�����
                         
    private long         sourceTime;                            // ����Դʱ��
                         
    private String       code;                                  // ��Ʊ����
                         
    private String       name;                                  // ��Ʊ����
                         
    private int          stktype          = -1;                 // ��Ʊ����
                                          
    private float        yesterday;                             // �������̼�
                         
    private float        open;                                  // ���տ��̼�
                         
    private float        high;                                  // ��߼�
                         
    private float        low;                                   // ��ͼ�
                         
    private float        now;                                   // �ּ�
                         
    private float        buyprice1;                             // ��һ
                         
    private float        buyprice2;                             // ���
                         
    private float        buyprice3;                             // ����
                         
    private float        buyprice4;                             // ����
                         
    private float        buyprice5;                             // ����
                         
    private float        buyvol1;                               // ������һ
                         
    private float        buyvol2;                               // ��������
                         
    private float        buyvol3;                               // ��������
                         
    private float        buyvol4;                               // ��������
                         
    private float        buyvol5;                               // ��������
                         
    private float        sellprice1;                            // ��һ
                         
    private float        sellprice2;                            // ����
                         
    private float        sellprice3;                            // ����
                         
    private float        sellprice4;                            // ����
                         
    private float        sellprice5;                            // ����
                         
    private float        sellvol1;                              // ������һ
                         
    private float        sellvol2;                              // ��������
                         
    private float        sellvol3;                              // ��������
                         
    private float        sellvol4;                              // ��������
                         
    private float        sellvol5;                              // ��������
                         
    private float        volume;                                // �ܳɽ���
                         
    private double       amount;                                // �ܳɽ����
                         
    private float        thedeal;                               // ����
                         
    private float        inside;                                // ����
                         
    private float        outside;                               // ����
                         
    private short        dealno;                                // �ɽ���ϸ����
                         
    private float        min5change;                            // 5�����ǵ���
                         
    private int          curdate;                               // ��ǰ����
                         
    private short        minutes;                               // ������
                         
    private float        volrate;                               // ����
                         
    private String       market;                                // �г�(��дSZ or SH)
                         
    private float        up               = 0.0F;               // �Ƿ�
                                          
    private float        uppercent        = 0.0F;               // �Ƿ��ٷֱ�
                                          
    private StockHisData uppercentHisData = new StockHisData(); // ��ʷ�ǵ���
                                          
    // �����������ֶ�
    private float        flux             = 0.0F;               // ���
                                          
    private int          WC;                                    // ί��
                         
    private float        WB               = 0.0F;               // ί��
                                          
    private float        average;                               // ����
                         
    private float        limitUp          = 0.0F;               // ��ͣ��
                                          
    private float        limitDown        = 0.0F;               // ��ͣ��
                                          
    private int          suspendMark;                           // ͣ�Ʊ�־ 1ͣ�� 2 ��ͣ��
                         
    private String       transferType;                          // ת�����͡��������ֶΡ�
                         
    private String       transferState;                         // ת��״̬���������ֶΡ�
                         
    private int          marginTradMark;                        // ������ȯ��ʶ ��0:�� �� 1:���� �� 2:��ȯ �� 3:������ȯ��
                         
    private String       stockCode;
                         
    private float        sellDealMoney;                         // ���̳ɽ����
                         
    private float        buyDealMoney;                          // ���̳ɽ����
                         
    private float        turnoverRate;                          // ������
                         
    private float        ltag;                                  // ��ͨA��
                         
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
