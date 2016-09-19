package com.thinkive.market.service.conn.push;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.log4j.Logger;
import com.thinkive.base.util.ByteStrHelper;
import com.thinkive.base.util.MyLog;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.util.HQHelper;
import com.thinkive.hqwarn.util.ServerConfig;

/**
 * @描述: 成交明细数据 从转码机接收数据
 * @版权: Copyright (c) 2013
 * @公司: 思迪科技
 * @作者: 熊攀
 * @版本: 1.0
 * @创建日期: 2014-9-7
 * @创建时间: 上午11:15:27
 */
public class Updater10001 extends Updater
{
    private static Logger  logger = Logger.getLogger(Updater10001.class);
                                  
    private static Updater updater;
                           
    private Updater10001()
    {
    }
    
    public static Updater getInstance()
    {
        synchronized (logger)
        {
            
            if ( updater == null )
            {
                updater = new Updater10001();
                updater.setName("Updater10001");
                updater.start();
            }
        }
        return updater;
    }
    
    /**
     * @描述：更新成交明细
     * @作者：熊攀
     * @时间：2012-5-4 下午5:34:52
     * @param data
     */
    @SuppressWarnings("unused")
    @Override
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
            
            short minute = dataBuffer.getShort();
            float now = dataBuffer.getFloat();
            int thedeal = dataBuffer.getInt();
            short flag = dataBuffer.getShort();
            float yesterday = dataBuffer.getFloat();
            
            stock.setYesterday(yesterday);
            
            float dealMoney = now * thedeal * 100;
            
            if ( flag == 0 )
            {
                stock.setBuyDealMoney(dealMoney);
                
            }
            else if ( flag == 1 )
            {
                stock.setSellDealMoney(dealMoney);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            MyLog.serverLog.warn("	--	@行情推送	--	推送成交明细数据出错", e);
        }
    }
}
