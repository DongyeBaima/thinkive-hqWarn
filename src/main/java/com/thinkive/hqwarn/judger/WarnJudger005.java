package com.thinkive.hqwarn.judger;

import java.util.List;
import com.thinkive.hq.bean.RealTimeData;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * @描述: 火箭发射 触发器
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年6月27日 下午3:27:43
 */
public class WarnJudger005 extends WarnJudger
{
    
    private final int HIS_RANGE_MINUTE = 3;// 触发器需要缓存的历史分钟数
    
    @Override
    public String getMessageContent(Stock stock, WarnInfo warnInfo)
    {
        // TODO Auto-generated method stub
        return "触发火箭发射";
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
        
        // -- -- 判断当前缓存数据是否小于HIS_RANGE_MINUTE分钟
        if ( nodeList.size() < HIS_RANGE_MINUTE + 1 )
        {
            return false;
        }
        
        // -- -- 判断HIS_RANGE_MINUTE分钟前的涨跌幅是否小于0 ，
        int index = nodeList.get(HIS_RANGE_MINUTE);
        if ( hisList.get(index).getData() <= 0 )
        {
            return false;
        }
        
        // -- -- 判断是否HIS_RANGE_MINUTE分钟内没分钟上涨 >= 0.5%
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
        
        // -- -- 判断HIS_RANGE_MINUTE分钟内是否有涨幅超过4%的
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
