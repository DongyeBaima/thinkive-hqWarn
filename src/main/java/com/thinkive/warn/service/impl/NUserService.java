package com.thinkive.warn.service.impl;

import com.thinkive.base.jdbc.DataRow;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.warn.service.AbstractDBService;

public class NUserService extends AbstractDBService<UserInfo>
{
    private final String tableName = "t_userinfo";
    
    @Override
    public String getInsertSql(UserInfo t)
    {
        // TODO Auto-generated method stub
        String userId = t.getUserID();
        String token = t.getToken();
        String mobileId = t.getMobileId();
        int zoneId = t.getZoneID();
        int tableId = t.getTableID();
        int osId = t.getOsId();
        
        String sql = "insert into " + tableName
                + " (user_id,table_id,zone_id,os_id,token,mobile_id,create_date,modified_date) values ('" + userId
                + "'," + tableId + "," + zoneId + "," + osId + ",'" + token + "','" + mobileId + "',now(),now())";
                
        return sql;
    }
    
    @Override
    public String getDeleteSql(UserInfo t)
    {
        // TODO Auto-generated method stub
        String userId = t.getUserID();
        String sql = "delete from " + tableName + " where user_id = '" + userId + "'";
        
        return sql;
    }
    
    @Override
    public String getSyncDeleteSql(UserInfo t)
    {
        // TODO Auto-generated method stub
        return getDeleteSql(t);
    }
    
    @Override
    public String getUpdateSql(UserInfo t)
    {
        // TODO Auto-generated method stub
        String userId = t.getUserID();
        String token = t.getToken();
        String mobileId = t.getMobileId();
        int zoneId = t.getZoneID();
        int tableId = t.getTableID();
        int osId = t.getOsId();
        
        StringBuilder sql = new StringBuilder("update " + tableName + " set modified_date = now() ");
        
        if ( osId != 0 )
        {
            sql.append(", os_id = " + osId);
        }
        addCondition(sql, ",", "zone_id", zoneId);
        addCondition(sql, ",", "table_id", tableId);
        addCondition(sql, ",", "token", token);
        addCondition(sql, ",", "mobile_id", mobileId);
        
        sql.append(" where user_id = '" + userId + "'");
        
        return sql.toString();
    }
    
    @Override
    public String getQuerySql(UserInfo t)
    {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder(
                "select user_id , table_id , zone_id , os_id , token , mobile_id from " + tableName + " where true ");
        if ( t == null )
        {
            return sql.toString();
        }
        
        String userId = t.getUserID();
        Integer tableId = t.getTableID();
        Integer zoneId = t.getZoneID();
        
        addCondition(sql, "and", "user_id", userId);
        addCondition(sql, "and", "table_id", tableId);
        addCondition(sql, "and", "zone_id", zoneId);
        
        return sql.toString();
    }
    
    @Override
    public UserInfo transform(DataRow data)
    {
        // TODO Auto-generated method stub
        String userId = data.getString("user_id");
        int tableId = data.getInt("table_id");
        int zoneId = data.getInt("zone_id");
        int osId = data.getInt("os_id");
        String token = data.getString("token");
        String mobileId = data.getString("mobile_id");
        
        UserInfo user = new UserInfo(userId, tableId, zoneId);
        user.setOsId(osId);
        user.setToken(token);
        user.setMobileId(mobileId);
        
        return user;
    }
}
