package com.thinkive.hqwarn.judger;

import java.util.List;
import com.thinkive.hq.bean.RealTimeData;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * @����: ������� ������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��6��27�� ����3:27:43
 */
public class WarnJudger005 extends WarnJudger
{
    
    private final int HIS_RANGE_MINUTE = 3;// ��������Ҫ�������ʷ������
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return "�����������";
    }
    
    @Override
    protected double setTriggervalue(Stock stock)
    {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    protected int setTrggerRangeType()
    {
        // TODO Auto-generated method stub
        return TRGGER_RANGE_TYPE_DOWN;
    }
    
    @Override
    protected boolean isTrigger(Stock stock)
    {
        // TODO Auto-generated method stub
        
        List<Integer> nodeList = stock.getUppercentHisData().getMinuteNodeIndexList();
        List<RealTimeData> hisList = stock.getUppercentHisData().getHisDataList();
        
        // -- -- �жϵ�ǰ���������Ƿ�С��HIS_RANGE_MINUTE����
        if ( nodeList.size() < HIS_RANGE_MINUTE + 1 )
        {
            return false;
        }
        
        // -- -- �ж�HIS_RANGE_MINUTE����ǰ���ǵ����Ƿ�С��0 ��
        int index = nodeList.get(HIS_RANGE_MINUTE);
        if ( hisList.get(index).getData() <= 0 )
        {
            return false;
        }
        
        // -- -- �ж��Ƿ�HIS_RANGE_MINUTE������û�������� >= 0.5%
        float uppercent = hisList.get(nodeList.get(0)).getData();
        float hisUppercent = 0;
        for (int i = 1; i <= HIS_RANGE_MINUTE; i++)
        {
            index = nodeList.get(i);
            
            hisUppercent = hisList.get(index).getData();
            
            if ( (uppercent - hisUppercent) > 0.5 )
            {
                uppercent = hisUppercent;
            }
            else
            {
                return false;
            }
        }
        
        // -- -- �ж�HIS_RANGE_MINUTE�������Ƿ����Ƿ�����4%��
        for (int i = 0, len = hisList.size(); i < len; i++)
        {
            if ( hisList.get(i).getData() > 4 )
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        // TODO Auto-generated method stub
        return 5;
    }
    
}
