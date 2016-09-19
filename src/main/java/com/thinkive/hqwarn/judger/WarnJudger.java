package com.thinkive.hqwarn.judger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.util.WarnCacheHelper;

public abstract class WarnJudger implements IWarnJudger
{
    protected Log              logger                 = LogFactory.getLog("Server");
                                                      
    protected static final int TRGGER_RANGE_TYPE_UP   = 1;
                                                      
    protected static final int TRGGER_RANGE_TYPE_DOWN = 2;
                                                      
    protected int              warnType;
                               
    protected double           triggerValue;
                               
    protected final int        triggerRangeType;                                    // 触发类型，[1:上限，2:下限]
                               
    public WarnJudger()
    {
        triggerRangeType = setTrggerRangeType();
        warnType = setWarnType();
    }
    
    @Override
    public List<WarnInfo> judger(Stock stock)
    {
        try
        {
            // TODO Auto-generated method stub
            
            if ( stock == null )
            {
                return null;
            }
            
            triggerValue = setTriggervalue(stock);
            
            if ( isTrigger(stock) )
            {
                return getTriggerList(stock);
            }
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            logger.warn("", e);
        }
        
        return null;
    }
    
    public List<WarnInfo> getTriggerList(Stock stock)
    {
        List<WarnInfo> allList = ServerCache.getWarnInfoCache(stock.getStockCode(), warnType);
        
        if ( allList == null )
        {
            return null;
        }
        
        List<WarnInfo> triggerList = new ArrayList<>();
        
        synchronized (allList)
        {
            int index = WarnCacheHelper.findSortIndexUnsafe(triggerValue, allList);
            
            WarnInfo warnInfo = null;
            int size = allList.size();
            if ( triggerRangeType == TRGGER_RANGE_TYPE_UP )
            {
                // -- -- 处理与 triggervalue 相同值的情况
                for (; index < size && index >= 0; index++)
                {
                    if ( WarnCacheHelper.warnInfoCompare(triggerValue, allList.get(index)) < 0 )
                    {
                        break;
                    }
                }
                
                for (int i = 0; i < index; i++)
                {
                    warnInfo = allList.get(i);
                    
                    if ( warnInfo != null && warnInfo.isValid() )
                    {
                        triggerList.add(warnInfo);
                    }
                    else if ( warnInfo.isDelete() )
                    {
                        WarnCacheHelper.destroydWarn(warnInfo);
                    }
                }
                
            }
            else if ( triggerRangeType == TRGGER_RANGE_TYPE_DOWN )
            {
                // -- -- 处理与 triggervalue 相同值的情况
                for (; index >= 0 && index < size; index--)
                {
                    if ( WarnCacheHelper.warnInfoCompare(triggerValue, allList.get(index)) > 0 )
                    {
                        break;
                    }
                }
                for (int i = index + 1; i < size; i++)
                {
                    warnInfo = allList.get(i);
                    if ( warnInfo != null && warnInfo.isValid() )
                    {
                        triggerList.add(warnInfo);
                    }
                    else if ( warnInfo.isDelete() )
                    {
                        WarnCacheHelper.destroydWarn(warnInfo);
                    }
                }
            }
        }
        
        return triggerList;
    }
    
    protected abstract double setTriggervalue(Stock stock);
    
    protected abstract int setTrggerRangeType();
    
    protected abstract boolean isTrigger(Stock stock);
    
    /**
     * @描述: 设置实现类的 warnType
     * @作者: 王嵊俊
     * @创建日期: 2016年5月12日 下午3:17:35
     */
    protected abstract int setWarnType();
    
    public int getWarnType()
    {
        return warnType;
    }
}
