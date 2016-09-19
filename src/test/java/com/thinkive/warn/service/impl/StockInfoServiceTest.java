package com.thinkive.warn.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.thinkive.hq.bean.Stock;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IStockInfoService;

public class StockInfoServiceTest
{
    
    private IStockInfoService stockInfoService = null;
    
    @Before
    public void before()
    {
        
        BeanFactory.init();
        
        stockInfoService = BeanFactory.getBean(Key.StockInfoService);
    }
    
    @Test
    public final void test()
    {
        Map<String, Stock> stockMap = new HashMap<>();
        
        for (int i = 0; i < 12; i++)
        {
            Stock stock = new Stock();
            String stockCode = "00000" + i;
            String market = "SH";
            String stockName = "¹ÉÆ±1";
            int stockType = i;
            
            stock.setStockCode(stockCode);
            stock.setName(stockName);
            stock.setMarket(market);
            stock.setStktype(stockType);
            
            stockMap.put(market + stockCode, stock);
            
            stockInfoService.insertOrUpdate(stockMap);
            
        }
        
    }
    
}
