package com.thinkive.hqwarn.judger;

import java.util.List;

import com.thinkive.hq.bean.RealTimeData;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * ��̨��ˮ
 * �������ɼ����ǳ���5%��3�����ڳ��ֵ�����4%���ϣ�����3������ÿ�����µ�>=0.5%
 * @author ����
 * @created 2016��6��29�� ����2:29:43
 * @since
 */
public class WarnJudger006 extends WarnJudger
{
    private final int HIS_RANGE_MINUTE = 3; // ��������Ҫ�������ʷ������
                                            
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return stock.getName() + " " + stock.getStockCode() + "��ǰ�۸�" + stock.getNow() + ",�ǵ���" + stock.getUppercent()
                + ",��̨��ˮ";
    }
    
    @Override
    protected double setTriggervalue(Stock stock)
    {
        return 0;
    }
    
    @Override
    protected int setTrggerRangeType()
    {
        return TRGGER_RANGE_TYPE_DOWN;
    }
    
    @Override
    protected boolean isTrigger(Stock stock)
    {
        //��ȡ���ӽڵ�����
        List<Integer> nodeIndexList = stock.getUppercentHisData().getMinuteNodeIndexList();
        //��ȡ��ʷ��������
        List<RealTimeData> hisDataList = stock.getUppercentHisData().getHisDataList();
        
        //�жϽڵ��Ƿ񻺴������Ƿ�С��3����
        if ( nodeIndexList.size() < HIS_RANGE_MINUTE + 1 )
        {
            return false;
        }
        
        //�ж�3����ǰ�Ĺɼ��Ƿ��Ƿ����5%
        int index = nodeIndexList.get(HIS_RANGE_MINUTE);
        if ( hisDataList.get(index).getData() <= 5 )
        {
            return false;
        }
        
        //�ж���3�������Ƿ�ÿ�����µ�0.5
        float beforeHisRangeMinuteUppercent = hisDataList.get(nodeIndexList.get(0)).getData();//��ȡ��ʷ�������ݵĵ��������ݵ��ǵ���
        float historyUpper = 0;
        for (int i = 1; i <= HIS_RANGE_MINUTE; i++)
        {
            index = nodeIndexList.get(i);//��ȡnodeIndexList�еĵڶ������ӹ�ȥ�Ľڵ�����
            
            historyUpper = hisDataList.get(index).getData();//����������ȡ�������ݵ�ǰһ���ӵ��ǵ���
            
            if ( (historyUpper - beforeHisRangeMinuteUppercent) > 0.5 )//���(ǰһ��  - ��һ��)[��ʾ����]���ӽڵ�ĵ�������0.5
            {
                beforeHisRangeMinuteUppercent = historyUpper;
            }
            else
            {
                return false;
            }
        }
        /**
         * 3min�ڳ����Ƿ���4% ��3minǰ�ǵ���>5%�� ��ÿ�����ǵ�����0.5%��ǰ����
         * ѭ��3min���ǵ������ݣ���    �����ǵ���  ���Է�������[3minǰ�ǵ�������   -  3min֮��ʵʱ�ǵ�������]   ���������4%����� 
         */
        //�ж�3�������Ƿ��е�������4%��  [ҵ��ȷ�����4%]
        float realTimeData = 0f;//3min��ʵʱ�ǵ�������
        float beforeThreeData = hisDataList.get(0).getData();//3minǰ�ǵ�������
        for (int i = 0, len = hisDataList.size(); i < len; i++)
        {
            realTimeData = hisDataList.get(i).getData();
            
            if ( (beforeThreeData - realTimeData) > 4 )//��ÿһ���ǵ������ݽ��бȽ�;�ʼΪ5%����������4%��< 1%
            {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected int setWarnType()
    {
        return 6;
    }
}
