package com.thinkive.warn.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkive.base.jdbc.DataRow;
import com.thinkive.base.jdbc.JdbcTemplate;
import com.thinkive.base.jdbc.session.Session;
import com.thinkive.base.jdbc.session.SessionFactory;
import com.thinkive.base.util.StringHelper;
import com.thinkive.warn.factory.BeanFactory;

public abstract class AbstractDBService<T> implements IDBService<T>
{
    protected final JdbcTemplate template = BeanFactory.getBean(BeanFactory.Key.DBTemplate);
                                          
    protected final String       id;
                                 
    public AbstractDBService()
    {
        // TODO Auto-generated constructor stub
        id = null;
    }
    
    public AbstractDBService(String id)
    {
        // TODO Auto-generated constructor stub
        this.id = id;
    }
    
    @Override
    public int delete(List<T> list)
    {
        return executSql(getDelete(list));
    }
    
    // -- -- -- -- -- -- -- -- -- -- 【 增 】  -- -- -- -- -- -- -- -- -- --
    
    @Override
    public int insert(T t)
    {
        return executSql(getInsertOrUpdate(t));
    }
    
    public List<String> getInsertOrUpdate(T t)
    {
        List<String> list = new ArrayList<>();
        if ( t == null )
        {
            return list;
        }
        
        String sql = null;
        if ( exist(t) )
        {
            sql = getUpdateSql(t);
        }
        else
        {
            sql = getInsertSql(t);
        }
        list.add(sql);
        
        return list;
    }
    
    @Override
    public int insert(List<T> list)
    {
        return executSql(getInsertOrUpdate(list));
    }
    
    public List<String> getInsertOrUpdate(List<T> list)
    {
        List<String> sqlList = new ArrayList<>();
        if ( list == null || list.isEmpty() )
        {
            return sqlList;
        }
        for (T t : list)
        {
            sqlList.addAll(getInsertOrUpdate(t));
        }
        return sqlList;
    }
    
    public List<String> getInsert(List<T> list)
    {
        List<String> sqlList = new ArrayList<>();
        if ( list == null || list.isEmpty() )
        {
            return sqlList;
        }
        for (T t : list)
        {
            sqlList.addAll(getInsert(t));
        }
        return sqlList;
    }
    
    public List<String> getInsert(T t)
    {
        List<String> list = new ArrayList<>();
        if ( t != null )
        {
            list.add(getInsertSql(t));
        }
        return list;
    }
    
    // -- -- -- -- -- -- -- -- -- -- 【 查 】  -- -- -- -- -- -- -- -- -- --
    
    @Override
    public boolean exist(T t)
    {
        List<T> dataList = query(t);
        
        return !(dataList == null || dataList.isEmpty());
    }
    
    public boolean exist(T t, Session session)
    {
        List<T> dataList = query(t, session);
        
        return !(dataList == null || dataList.isEmpty());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<T> query(T t)
    {
        String sql = getQuerySql(t);
        return transform(template.query(sql));
    }
    
    @Override
    public List<T> query(List<T> list)
    {
        List<T> resultList = new ArrayList<>();
        if ( list.size() == 0 )
        {
            return resultList;
        }
        if ( list.size() == 1 )
        {
            return query(list.get(0));
        }
        for (T t : list)
        {
            resultList.addAll(query(t));
        }
        return resultList;
    }
    
    @SuppressWarnings("unchecked")
    public List<T> query(T t, Session session)
    {
        String sql = getQuerySql(t);
        return transform(session.query(sql));
    }
    
    // -- -- -- -- -- -- -- -- -- -- 【 工具 】  -- -- -- -- -- -- -- -- -- --
    public Session getSession()
    {
        if ( !StringHelper.isEmpty(id) )
            return SessionFactory.getSession(id);
        else
            return SessionFactory.getSession();
    }
    
    public abstract String getInsertSql(T t);
    
    public abstract String getDeleteSql(T t);
    
    public abstract String getSyncDeleteSql(T t);
    
    public abstract String getUpdateSql(T t);
    
    public abstract String getQuerySql(T t);
    
    public abstract T transform(DataRow data);
    
    public List<T> transform(List<DataRow> dataList)
    {
        List<T> list = new ArrayList<>(dataList.size());
        
        for (DataRow data : dataList)
        {
            list.add(transform(data));
        }
        return list;
    }
    
    @Override
    public int delete(T t)
    {
        return executSql(getDelete(t));
    }
    
    @Override
    public List<String> getDelete(List<T> list)
    {
        // TODO Auto-generated method stub
        List<String> sqlList = new ArrayList<>(list.size());
        
        for (T t : list)
        {
            if ( t != null )
            {
                sqlList.add(getDeleteSql(t));
            }
        }
        
        return sqlList;
    }
    
    @Override
    public List<String> getDelete(T t)
    {
        // TODO Auto-generated method stub
        List<String> sqlList = new ArrayList<>();
        
        if ( t != null )
        {
            sqlList.add(getDeleteSql(t));
        }
        
        return sqlList;
    }
    
    @Override
    public int update(List<T> list)
    {
        return executSql(getUpdate(list));
    }
    
    private List<String> getUpdate(List<T> list)
    {
        // TODO Auto-generated method stub
        List<String> sqlList = new ArrayList<>(list.size());
        
        for (T t : list)
        {
            if ( t != null )
            {
                sqlList.add(getUpdateSql(t));
            }
        }
        
        return sqlList;
    }
    
    @Override
    public int update(T t)
    {
        return executSql(getUpdate(t));
    }
    
    public List<String> getUpdate(T t)
    {
        // TODO Auto-generated method stub
        List<String> sqlList = new ArrayList<>();
        if ( t != null )
        {
            sqlList.add(getUpdateSql(t));
        }
        return sqlList;
    }
    
    @Override
    public int sync(List<T> list)
    {
        return executSql(getSync(list));
    }
    
    public List<String> getSync(List<T> list)
    {
        // TODO Auto-generated method stub
        List<String> sqlList = new ArrayList<>();
        if ( list == null || list.isEmpty() )
        {
            return sqlList;
        }
        String deleteSql = getSyncDeleteSql(list.get(0));
        sqlList.add(deleteSql);
        sqlList.addAll(getInsert(list));
        
        return sqlList;
    }
    
    // -- -- -- -- -- -- -- -- -- -- 【 带session 】  -- -- -- -- -- -- -- -- -- --
    
    public int insert(T t, Session session)
    {
        return executSql(session, getInsertOrUpdate(t));
    }
    
    public int insert(List<T> t, Session session)
    {
        return executSql(session, getInsertOrUpdate(t));
    }
    
    public int delete(T t, Session session)
    {
        return executSql(session, getDelete(t));
    }
    
    public int delete(List<T> list, Session session)
    {
        return executSql(session, getDelete(list));
    }
    
    public int update(T t, Session session)
    {
        return executSql(session, getUpdate(t));
    }
    
    public int update(List<T> list, Session session)
    {
        return executSql(session, getUpdate(list));
    }
    
    // -- -- -- -- -- -- -- -- -- -- 【 executSql 】  -- -- -- -- -- -- -- -- -- --
    
    public int executSql(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return executSql(session, sql);
        }
        finally
        {
            if ( session != null )
            {
                session.close();
                session = null;
            }
        }
    }
    
    public int executSql(List<String> sqlList)
    {
        Session session = null;
        try
        {
            session = getSession();
            return executSql(session, sqlList);
        }
        finally
        {
            if ( session != null )
            {
                session.close();
                session = null;
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public int executSql(List<String> sqlList, List<String>... sqlListArray)
    {
        Session session = null;
        try
        {
            session = getSession();
            return executSql(session, sqlList, sqlListArray);
        }
        finally
        {
            if ( session != null )
            {
                session.close();
                session = null;
            }
        }
    }
    
    public int executSql(Session session, String sql)
    {
        return session.update(sql);
    }
    
    public int executSql(Session session, List<String> sqlList)
    {
        int result = 0;
        if ( sqlList.size() == 1 )
        {
            result = session.update(sqlList.get(0));
        }
        else if ( sqlList.size() != 0 )
        {
            String sqlArray[] = sqlList.toArray(new String[sqlList.size()]);
            for (int n : session.batchUpdate(sqlArray))
            {
                result += n;
            }
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public int executSql(Session session, List<String> sqlList, List<String>... sqlListArray)
    {
        int result = 0;
        for (List<String> list : sqlListArray)
        {
            if ( list != null )
            {
                sqlList.addAll(list);
            }
        }
        String sqlArray[] = sqlList.toArray(new String[sqlList.size()]);
        for (int n : session.batchUpdate(sqlArray))
        {
            result += n;
        }
        
        return result;
    }
    
    public void addCondition(StringBuilder sb, String pre, String fieldName, Object value)
    {
        if ( value == null )
        {
            return;
        }
        
        if ( pre != null )
        {
            sb.append(" " + pre + " ");
        }
        
        sb.append(fieldName + " = ");
        if ( value instanceof Number )
        {
            sb.append(value + " ");
        }
        else
        {
            sb.append("'" + value + "'");
        }
    }
}
