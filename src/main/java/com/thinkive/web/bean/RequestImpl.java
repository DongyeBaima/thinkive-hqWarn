package com.thinkive.web.bean;

import java.util.HashMap;
import java.util.Map;
import com.thinkive.base.util.MyStringUtil;

/**
 * @����: �������ķ���������汾��Ϣ
 * @��Ȩ: Copyright (c) 2012
 * @��˾: ˼�ϿƼ�
 * @����: ��֪֮
 * @�汾: 1.0
 * @��������: 2012-3-14
 * @����ʱ��: ����4:33:52
 */
public class RequestImpl implements Request
{
    // ����汾��
    private int    versionNo = 0;
                             
    // ���ܺ���
    private int    funcNo    = 0;
                             
    /**
     * ��������
     */
    private byte[] data;
                   
    /**
     * ��ˮ��
     */
    private int    flowNo    = 0;
                             
    /**
     * ��֧��
     */
    private int    branchNo  = 0;
                             
    public int getFlowNo()
    {
        return flowNo;
    }
    
    public void setFlowNo(int flowNo)
    {
        this.flowNo = flowNo;
    }
    
    // �������
    private Map<String, Object> params = new HashMap<>();
    
    public void clear()
    {
        params.clear();
    }
    
    /**
     * ��ù��ܺ���
     * 
     * @return
     */
    public int getFuncNo()
    {
        return funcNo;
    }
    
    public byte[] getData()
    {
        return data;
    }
    
    public void setData(byte[] data)
    {
        this.data = data;
    }
    
    /**
     * ����������
     * 
     * @param name
     * @return
     */
    public Object getParameter(String name)
    {
        return params.get(name);
    }
    
    public Map<String, Object> getParameter()
    {
        return params;
    }
    
    public int getVersionNo()
    {
        return versionNo;
    }
    
    public void setVersionNo(int versionNo)
    {
        this.versionNo = versionNo;
    }
    
    /**
     * ���ù��ܺ���
     * 
     * @param funcNo
     */
    public void setFuncNo(int funcNo)
    {
        this.funcNo = funcNo;
    }
    
    /**
     * ����������
     * 
     * @param name
     * @param value
     */
    public void addParameter(String name, Object value)
    {
        params.put(name, value);
    }
    
    public int getBranchNo()
    {
        return branchNo;
    }
    
    public void setBranchNo(int branchNo)
    {
        this.branchNo = branchNo;
    }
    
    public String toString()
    {
        return MyStringUtil.getString(params);
    }
    
}
