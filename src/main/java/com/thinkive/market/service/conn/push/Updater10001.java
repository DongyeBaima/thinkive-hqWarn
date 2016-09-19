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
 * @����: �ɽ���ϸ���� ��ת�����������
 * @��Ȩ: Copyright (c) 2013
 * @��˾: ˼�ϿƼ�
 * @����: ����
 * @�汾: 1.0
 * @��������: 2014-9-7
 * @����ʱ��: ����11:15:27
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
     * @���������³ɽ���ϸ
     * @���ߣ�����
     * @ʱ�䣺2012-5-4 ����5:34:52
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
            
            String stockCode = ByteStrHelper.getString(dataBuffer, 8);// 8���ֽ�
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
                MyLog.serverLog.warn("	--	@��������	--	�����в����ڸù�Ʊ��Ϣ��" + stockCode);
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
            MyLog.serverLog.warn("	--	@��������	--	���ͳɽ���ϸ���ݳ���", e);
        }
    }
}
