package com.thinkive.market.bean;

/**
 * @����: �������ķ�����״̬����
 * @��Ȩ: Copyright (c) 2012
 * @��˾: ˼�ϿƼ�
 * @����: ��֪֮
 * @�汾: 1.0
 * @��������: 2012-3-29
 * @����ʱ��: ����2:25:32
 */
public class MCState
{
    private String  initDate;    // ���ػ����ʼ������
                    
    private String  curHqDate;
                    
    private boolean isNeedUpdate;
                    
    private long    dbfTime;     // dbf�ļ���ʱ��
                    
    private long    hkdbfTime;   // �۹�ͨ����Ԫ�ļ���ʱ�� ������ xiongpan 2014-11-20
                    
    private String  hkInitDate;  // ���ظ۹ɳ�ʼ������ ���ӿ� 2014-12-31
                    
    private String  hkCurHqDate;
                    
    public long getDbfTime()
    {
        return dbfTime;
    }
    
    public void setDbfTime(long dbfTime)
    {
        this.dbfTime = dbfTime;
    }
    
    public String getInitDate()
    {
        return initDate;
    }
    
    public void setInitDate(String initDate)
    {
        this.initDate = initDate;
    }
    
    public String getCurHqDate()
    {
        return curHqDate;
    }
    
    public void setCurHqDate(String curHqDate)
    {
        this.curHqDate = curHqDate;
    }
    
    public boolean isNeedUpdate()
    {
        return isNeedUpdate;
    }
    
    public void setNeedUpdate(boolean isNeedUpdate)
    {
        this.isNeedUpdate = isNeedUpdate;
    }
    
    public long getHkdbfTime()
    {
        return hkdbfTime;
    }
    
    public void setHkdbfTime(long hkdbfTime)
    {
        this.hkdbfTime = hkdbfTime;
    }
    
    public String getHkInitDate()
    {
        return hkInitDate;
    }
    
    public void setHkInitDate(String hkInitDate)
    {
        this.hkInitDate = hkInitDate;
    }
    
    public String getHkCurHqDate()
    {
        return hkCurHqDate;
    }
    
    public void setHkCurHqDate(String hkCurHqDate)
    {
        this.hkCurHqDate = hkCurHqDate;
    }
    
    @Override
    public String toString()
    {
        return "MCState [initDate=" + initDate + ", curHqDate=" + curHqDate + ", isNeedUpdate=" + isNeedUpdate
                + ", dbfTime=" + dbfTime + ", hkdbfTime=" + hkdbfTime + ", hkInitDate=" + hkInitDate + ", hkCurHqDate="
                + hkCurHqDate + "]";
    }
    
}
