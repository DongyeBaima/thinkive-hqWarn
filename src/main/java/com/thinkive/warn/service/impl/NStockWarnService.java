package com.thinkive.warn.service.impl;

import com.thinkive.base.jdbc.DataRow;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.util.WarnCacheHelper;
import com.thinkive.warn.service.AbstractDBService;

public class NStockWarnService extends AbstractDBService<WarnInfo>
{
    
    @Override
    public String getInsertSql(WarnInfo t)
    {
        // TODO Auto-generated method stub
        String userId = t.getUserID();
        String stockCode = t.getStockCode();
        Integer warnType = t.getWarnType();
        Float warnValue = t.getWarnValue();
        Integer warnCount = t.getWarnCount();
        Integer validWarnCount = t.getValidWarnCount();
        
        if ( userId == null || stockCode == null || warnType == null || warnValue == null || warnCount == null
                || validWarnCount == null )
        {
            throw new IllegalArgumentException("    --  @Insert --   无效的参数 : " + t);
        }
        String tableName = WarnCacheHelper.getWarnTabelName(userId);
        
        String sql = "insert into " + tableName
                + " (user_id , stock_code,warn_type,warn_value,warn_d_valid_count,warn_valid_count,create_date,modified_date) values ('"
                + userId + "','" + stockCode + "'," + warnType + "," + warnValue + "," + warnCount + " ,"
                + validWarnCount + ",now(),now())";
                
        return sql;
    }
    
    @Override
    public String getDeleteSql(WarnInfo t)
    {
        // TODO Auto-generated method stub
        String userId = t.getUserID();
        String stockCode = t.getStockCode();
        Integer warnType = t.getWarnType();
        if ( userId == null )
        {
            throw new IllegalArgumentException("    --  @Insert --   无效的参数 : " + t);
        }
        String tableName = WarnCacheHelper.getWarnTabelName(userId);
        StringBuilder sql = new StringBuilder("delete from " + tableName);
        
        addCondition(sql, "where", "user_id", userId);
        addCondition(sql, "and", "stock_code", stockCode);
        addCondition(sql, "and", "warn_type", warnType);
        
        return sql.toString();
    }
    
    @Override
    public String getSyncDeleteSql(WarnInfo t)
    {
        // TODO Auto-generated method stub
        String userId = t.getUserID();
        String stockCode = t.getStockCode();
        return getDeleteSql(new WarnInfo(userId, stockCode, null));
    }
    
    @Override
    public String getUpdateSql(WarnInfo t)
    {
        // TODO Auto-generated method stub
        String userId = t.getUserID();
        String stockCode = t.getStockCode();
        Integer warnType = t.getWarnType();
        Float warnValue = t.getWarnValue();
        Integer warnCount = t.getWarnCount();
        Integer validWarnCount = t.getValidWarnCount();
        
        if ( userId == null )
        {
            throw new IllegalArgumentException("    --  @Insert --   无效的参数 : " + t);
        }
        String tableName = WarnCacheHelper.getWarnTabelName(userId);
        StringBuilder sql = new StringBuilder("update " + tableName + " set modified_date = now() ");
        addCondition(sql, ",", "warn_value", warnValue);
        addCondition(sql, ",", "warn_d_valid_count", warnCount);
        addCondition(sql, ",", "warn_valid_count", validWarnCount);
        
        addCondition(sql, " where ", "user_id", userId);
        addCondition(sql, "and", "stock_code", stockCode);
        addCondition(sql, "and", "warn_type", warnType);
        
        return sql.toString();
    }
    
    @Override
    public String getQuerySql(WarnInfo t)
    {
        // TODO Auto-generated method stub
        String userId = t.getUserID();
        String stockCode = t.getStockCode();
        Integer warnType = t.getWarnType();
        if ( userId == null )
        {
            throw new IllegalArgumentException("    --  @Insert --   无效的参数 : " + t);
        }
        String tableName = WarnCacheHelper.getWarnTabelName(userId);
        StringBuilder sql = new StringBuilder(
                "select user_id , stock_code,warn_value,warn_type,warn_d_valid_count,warn_valid_count from "
                        + tableName);
        addCondition(sql, "where", "user_id", userId);
        addCondition(sql, "and", "stock_code", stockCode);
        addCondition(sql, "and", "warn_type", warnType);
        return sql.toString();
    }
    
    @Override
    public WarnInfo transform(DataRow data)
    {
        // TODO Auto-generated method stub
        String userId = data.getString("user_id");
        String stockCode = data.getString("stock_code");
        Integer warnType = data.getInt("warn_type");
        Float warnValue = data.getFloat("warn_value");
        Integer warnCount = data.getInt("warn_d_valid_count");
        Integer validWarnCount = data.getInt("warn_valid_count");
        
        WarnInfo warnInfo = new WarnInfo(userId, stockCode, warnType, warnValue, warnCount, validWarnCount);
        
        return warnInfo;
    }
    
    /**@描述: 重置提醒有效次数
     * @作者: 王嵊俊
     * @创建日期: 2016年9月5日 下午5:37:26
     */
    public void resetWarnCount(String tableName)
    {
        String sql = " update " + tableName + " set modified_date = now() , warn_valid_count = warn_d_valid_count ";
        template.update(sql);
    }
}
