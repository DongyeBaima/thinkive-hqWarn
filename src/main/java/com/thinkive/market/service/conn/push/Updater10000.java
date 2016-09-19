package com.thinkive.market.service.conn.push;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.thinkive.base.util.ByteStrHelper;
import com.thinkive.base.util.MyLog;
import com.thinkive.base.util.MyStringUtil;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.cache.HQState;
import com.thinkive.hqwarn.cache.QueueManager;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.util.HQHelper;
import com.thinkive.hqwarn.util.ServerConfig;

/**
 * @描述: 处理实时行情推送
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月10日 下午5:04:41
 */
public class Updater10000 extends Updater
{
    
    private static Updater updater;
    
    private Updater10000()
    {
    }
    
    public static Updater getInstance()
    {
        if ( updater == null )
            synchronized (Updater10000.class)
            {
                if ( updater == null )
                {
                    updater = new Updater10000();
                    updater.setName("Updater10000");
                    updater.start();
                }
            }
        return updater;
    }
    
    public void update(byte[] b)
    {
        try
        {
            ByteBuffer dataBuffer = ByteBuffer.wrap(b);
            dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
            
            String stockCode = ByteStrHelper.getString(dataBuffer, 8);// 8个字节
            String market = stockCode.substring(0, 2);
            String code = stockCode.substring(2);
            
            if ( !HQHelper.canReceive(market) )
            {
                return;
            }
            
            if ( ServerConfig.HQ_MARKET_HK.equals(market) )
            {
                code = code.substring(1);
                stockCode = market + code;
            }
            
            Stock stock = ServerCache.getStock(stockCode);
            
            if ( stock == null )
            {
                MyLog.serverLog.warn("	--	@行情推送	--	缓存中不存在该股票信息：" + stockCode);
                return;
            }
            
            float high = dataBuffer.getFloat();
            float low = dataBuffer.getFloat();
            float now = dataBuffer.getFloat();
            float buy = dataBuffer.getFloat();
            float sell = dataBuffer.getFloat();
            float buyprice1 = dataBuffer.getFloat();
            float buyprice2 = dataBuffer.getFloat();
            float buyprice3 = dataBuffer.getFloat();
            float buyprice4 = dataBuffer.getFloat();
            float buyprice5 = dataBuffer.getFloat();
            float buyvol1 = dataBuffer.getFloat();
            float buyvol2 = dataBuffer.getFloat();
            float buyvol3 = dataBuffer.getFloat();
            float buyvol4 = dataBuffer.getFloat();
            float buyvol5 = dataBuffer.getFloat();
            float sellprice1 = dataBuffer.getFloat();
            float sellprice2 = dataBuffer.getFloat();
            float sellprice3 = dataBuffer.getFloat();
            float sellprice4 = dataBuffer.getFloat();
            float sellprice5 = dataBuffer.getFloat();
            float sellvol1 = dataBuffer.getFloat();
            float sellvol2 = dataBuffer.getFloat();
            float sellvol3 = dataBuffer.getFloat();
            float sellvol4 = dataBuffer.getFloat();
            float sellvol5 = dataBuffer.getFloat();
            float volume = dataBuffer.getFloat();
            float amount = dataBuffer.getFloat();
            float thedeal = dataBuffer.getFloat();
            float inside = dataBuffer.getFloat();
            float outside = dataBuffer.getFloat();
            float min5change = dataBuffer.getFloat();
            
            short minutes = dataBuffer.getShort();
            
            String isSuspended = ByteStrHelper.getString(dataBuffer, 1); // 停牌标识
            int marginTradMark = MyStringUtil.strToInt(ByteStrHelper.getString(dataBuffer, 1)); // 融资融券
            
            String transferState = ByteStrHelper.getString(dataBuffer, 1); // 转让类型【新三板字段】
            String transferType = ByteStrHelper.getString(dataBuffer, 1); // 转让状态【新三板字段】
            
            ByteStrHelper.getString(dataBuffer, 6); // 保留字段
            
            stock.setMinutes(minutes);
            stock.setCode(code);
            stock.setMarket(market);
            stock.setBuyprice1(buyprice1);
            stock.setBuyprice2(buyprice2);
            stock.setBuyprice3(buyprice3);
            stock.setBuyprice4(buyprice4);
            stock.setBuyprice5(buyprice5);
            stock.setSellprice1(sellprice1);
            stock.setSellprice2(sellprice2);
            stock.setSellprice3(sellprice3);
            stock.setSellprice4(sellprice4);
            stock.setSellprice5(sellprice5);
            stock.setBuyvol1(buyvol1);
            stock.setBuyvol2(buyvol2);
            stock.setBuyvol3(buyvol3);
            stock.setBuyvol4(buyvol4);
            stock.setBuyvol5(buyvol5);
            stock.setSellvol1(sellvol1);
            stock.setSellvol2(sellvol2);
            stock.setSellvol3(sellvol3);
            stock.setSellvol4(sellvol4);
            stock.setSellvol5(sellvol5);
            stock.setMin5change(min5change);
            
            stock.setOpen(buy);// 今开
            stock.setHigh(high);
            stock.setLow(low);
            stock.setVolrate(sell);// 量比
            
            /* 集合竞价时间内，现价是0，设为昨收 */
            if ( now == 0 )
            {
                if ( buyprice1 != 0 && sellprice1 != 0 && buyprice1 == sellprice1 )
                {
                    stock.setNow(buyprice1);
                }
                else
                {
                    stock.setNow(stock.getYesterday());
                }
            }
            else
            {
                stock.setNow(now);
            }
            
            stock.setVolume(volume);
            stock.setAmount(amount);
            stock.setOutside(outside);
            stock.setInside(inside);
            stock.setThedeal(thedeal);
            
            // -- 停牌标识
            int issuspend = 2;
            if ( "T".equals(isSuspended) || "t".equals(isSuspended) )
            {
                issuspend = 1;// 停牌
            }
            else
            {
                issuspend = 2;
            }
            stock.setSuspendMark(issuspend);// 停牌标识
            
            stock.setMarginTradMark(marginTradMark);
            
            stock.setTransferType(transferType);
            stock.setTransferState(transferState);
            stock.setSourceTime(HQState.get(market).getSourceTime());
            
            HQHelper.calculate(stockCode);
            
            QueueManager.hqQueue.put(stock);
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            MyLog.serverLog.warn("	--	@行情推送	--	推送实时数据出错", e);
        }
    }
    
}
