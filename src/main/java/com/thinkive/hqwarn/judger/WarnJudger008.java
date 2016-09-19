package com.thinkive.hqwarn.judger;

import java.util.List;

import com.thinkive.hq.bean.RealTimeData;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * ָ�ɼ��µ�����2%��������3�����ڳ��ֵ���>=3%
 * ������
 * @author ����
 * @created 2016��7��4�� ����3:19:59
 * @since
 */
public class WarnJudger008 extends WarnJudger
{
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "��ǰ�۸�" + stock.getNow() + ",�ǵ���" + stock.getUppercent()
                + ",�����µ�!";
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
        //��ȡ3min�ڵ����ݵ�����
        List<Integer> nodeIndexList = stock.getUppercentHisData().getMinuteNodeIndexList();
        //��ȡ��ʷ�����б�
        List<RealTimeData> hisDataList = stock.getUppercentHisData().getHisDataList();
        
        //�����ж�3min��ĵĵ����Ƿ񳬹�3%[����3�����ڳ��ֵ���>=3%]
        if ( hisDataList.get(nodeIndexList.get(0)).getData() >= -3 )
        {
            return false;
        }
        
        //�ӵ�ǰʱ����ǰ��3min���ж��Ƿ��е�������2%��[�����жϵ�ǰ�ɼ��Ƿ��µ�����2%]
        for (int i = 0; i < hisDataList.size(); i++)
        {
            //�ж�3min��ǰ���ǵ����Ƿ������5%
            if ( hisDataList.get(i).getData() < -2 )
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
        return 8;
    }
    
}
