package com.thinkive.warn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkive.base.jdbc.DataRow;
import com.thinkive.base.jdbc.JdbcTemplate;
import com.thinkive.hq.bean.Stock;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IStockInfoService;

/**@描述: 股票信息服务类， 用于返回给标准版客户端股票基础信息
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年8月31日 上午10:50:18
 */
public class StockInfoService implements IStockInfoService
{
    private final String       TABLE_NAME = "t_stockinfo";
                                          
    private final JdbcTemplate template   = BeanFactory.getBean(Key.DBTemplate);
                                          
    /* (non-Javadoc)
     * @see com.thinkive.warn.service.impl.IStockInfoService#insertOrUpdate(java.util.Map)
     */
    @Override
    public void insertOrUpdate(Map<String, Stock> stockMap)
    {
        String insertSql = "insert into " + TABLE_NAME
                + "(stock_code,stock_name,stock_type,create_date,modified_date) values(?,?,?,now(),now())";
        String updateSql = "update " + TABLE_NAME
                + " set modified_date = now(), stock_name = ? , stock_type=? where stock_code = ? and (stock_name != ? or stock_type != ?)";
        Map<String, DataRow> dbDataMap = new HashMap<>();
        
        List<DataRow> dbDataList = query();
        if ( dbDataList != null )
        {
            for (DataRow data : dbDataList)
            {
                String stockCode = data.getString("stock_code");
                dbDataMap.put(stockCode, data);
            }
        }
        
        List<Object[]> insertList = new ArrayList<>();
        List<Object[]> updateList = new ArrayList<>();
        for (String stockCode : stockMap.keySet())
        {
            Stock stock = stockMap.get(stockCode);
            String stockName = stock.getName();
            int stockType = stock.getStktype();
            
            if ( dbDataMap.containsKey(stockCode) )
            {
                DataRow data = dbDataMap.get(stockCode);
                String stockName_db = data.getString("stock_name");
                int stockType_db = data.getInt("stock_type");
                
                if ( stockName.equals(stockName_db) && stockType_db == stockType )
                {
                    continue;
                }
                // -- 更新
                Object items[] = new Object[5];
                items[0] = stockName;
                items[1] = stockType;
                items[2] = stockCode;
                items[3] = stockName;
                items[4] = stockType;
                
                updateList.add(items);
            }
            else
            {
                // --插入
                Object items[] = new Object[3];
                items[0] = stockCode;
                items[1] = stockName;
                items[2] = stockType;
                
                insertList.add(items);
            }
        }
        
        if ( !insertList.isEmpty() )
        {
            Object[][] args = insertList.toArray(new Object[insertList.size()][3]);
            template.batchUpdate(insertSql, args);
        }
        if ( !updateList.isEmpty() )
        {
            Object[][] args = updateList.toArray(new Object[updateList.size()][3]);
            template.batchUpdate(updateSql, args);
        }
    }
    
    /* (non-Javadoc)
     * @see com.thinkive.warn.service.impl.IStockInfoService#query()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DataRow> query()
    {
        String sql = "select stock_code , stock_name , stock_type  from " + TABLE_NAME;
        return template.query(sql);
    }
}
