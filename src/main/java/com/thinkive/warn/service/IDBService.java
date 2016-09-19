package com.thinkive.warn.service;

import java.util.List;

public interface IDBService<T>
{
    /**
     * @����: ����
     * @����: ���ӿ�
     * @��������: 2016��9��2�� ����2:53:44
     * @param list
     * @return
     */
    public int insert(List<T> list);
    
    public int insert(T t);
    
    /**
     * @����: ɾ
     * @����: ���ӿ�
     * @��������: 2016��9��2�� ����2:53:55
     * @param list
     * @return
     */
    public int delete(List<T> list);
    
    public int delete(T t);
    
    /**
     * @����:ɾ
     * @����: ���ӿ�
     * @��������: 2016��9��2�� ����2:56:08
     * @param list
     * @param session
     * @return
     */
    public List<String> getDelete(List<T> list);
    
    public List<String> getDelete(T t);
    
    /**
     * @����: ��
     * @����: ���ӿ�
     * @��������: 2016��9��2�� ����2:54:08
     * @param list
     * @return
     */
    public int update(List<T> list);
    
    public int update(T t);
    
    /**
     * @����:ͬ�� 
     * @����: ���ӿ�
     * @��������: 2016��9��2�� ����2:54:19
     * @param list
     * @return
     */
    public int sync(List<T> list);
    
    /**
     * @����: ��
     * @����: ���ӿ�
     * @��������: 2016��9��2�� ����2:54:28
     * @param t
     * @return
     */
    public List<T> query(T t);
    
    /**
     * @����: �Ƿ���� 
     * @����: ���ӿ�
     * @��������: 2016��9��2�� ����3:25:26
     * @param t
     * @return
     */
    public boolean exist(T t);

    List<T> query(List<T> list);
}
