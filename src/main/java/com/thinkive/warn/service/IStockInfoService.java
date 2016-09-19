package com.thinkive.warn.service;

import java.util.List;
import java.util.Map;

import com.thinkive.base.jdbc.DataRow;
import com.thinkive.hq.bean.Stock;

public interface IStockInfoService
{
    
    void insertOrUpdate(Map<String, Stock> stockMap);
    
    List<DataRow> query();
    
}
