package com.thinkive.hqwarn.judger;

import java.util.List;

import com.thinkive.hq.bean.RealTimeData;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * ���ٷ���
 * ������ָ�ɼ��µ�����5%��������3�����ڳ����Ƿ�>=3%
 * @author ����
 * @created 2016��6��30�� ����1:20:12
 * @since
 */
public class WarnJudger007 extends WarnJudger
{
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "��ǰ�۸�" + stock.getNow() + ",�ǵ���" + stock.getUppercent()
                + ",���ٷ���!";
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
        //��ȡ3min��ʷ��������
        List<RealTimeData> hisDataList = stock.getUppercentHisData().getHisDataList();
        
        //�жϵ���ʱ����Ƿ��Ƿ񳬹�3%
        if ( hisDataList.get(nodeIndexList.get(0)).getData() < 3 )
        {
            return false;
        }
        
        //�ж�3min��ǰ����ǰʱ���Ƿ��е�����5%��
        int len = hisDataList.size();
        for (int i = 0; i < len; i++)
        {
            //�ж�3min���ڵ��ǵ����Ƿ������5%
            if ( hisDataList.get(i).getData() < -5 )
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
        return 7;
    }
}
