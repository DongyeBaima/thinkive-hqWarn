package com.thinkive.warn.service;

import java.util.List;

public interface IDBService<T>
{
    /**
     * @描述: 插入
     * @作者: 王嵊俊
     * @创建日期: 2016年9月2日 下午2:53:44
     * @param list
     * @return
     */
    public int insert(List<T> list);
    
    public int insert(T t);
    
    /**
     * @描述: 删
     * @作者: 王嵊俊
     * @创建日期: 2016年9月2日 下午2:53:55
     * @param list
     * @return
     */
    public int delete(List<T> list);
    
    public int delete(T t);
    
    /**
     * @描述:删
     * @作者: 王嵊俊
     * @创建日期: 2016年9月2日 下午2:56:08
     * @param list
     * @param session
     * @return
     */
    public List<String> getDelete(List<T> list);
    
    public List<String> getDelete(T t);
    
    /**
     * @描述: 改
     * @作者: 王嵊俊
     * @创建日期: 2016年9月2日 下午2:54:08
     * @param list
     * @return
     */
    public int update(List<T> list);
    
    public int update(T t);
    
    /**
     * @描述:同步 
     * @作者: 王嵊俊
     * @创建日期: 2016年9月2日 下午2:54:19
     * @param list
     * @return
     */
    public int sync(List<T> list);
    
    /**
     * @描述: 查
     * @作者: 王嵊俊
     * @创建日期: 2016年9月2日 下午2:54:28
     * @param t
     * @return
     */
    public List<T> query(T t);
    
    /**
     * @描述: 是否存在 
     * @作者: 王嵊俊
     * @创建日期: 2016年9月2日 下午3:25:26
     * @param t
     * @return
     */
    public boolean exist(T t);

    List<T> query(List<T> list);
}
