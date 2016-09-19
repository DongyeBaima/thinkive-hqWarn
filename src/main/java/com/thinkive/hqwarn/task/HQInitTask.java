package com.thinkive.hqwarn.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinkive.hqwarn.cache.HQState;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.netty.GateWayDao;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.market.bean.MCState;
import com.thinkive.timerengine.Task;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.warn.service.IStockInfoService;

/**
 * @描述: 行情初始化检测任务
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月12日 上午11:30:59
 */
public class HQInitTask implements Task
{
    private static Log logger = LogFactory.getLog("Server");
    
    /**
     * @描述:  判断该市场是否需要初始化,若需要则初始化数据
     * @作者: 王嵊俊
     * @创建日期: 2016年5月12日 上午11:14:57
     * @param initData
     * @param market
     * @return
     */
    private final void checkHQInit(String initData, String market)
    {
        if ( initData == null )
        {
            return;
        }
        
        HQState hqState = HQState.get(market);
        
        if ( hqState != null )
        {
            String curHqDate = hqState.getCurrentDate();
            
            if ( curHqDate == null || !initData.equals(curHqDate) )
            {
                logger.warn("	--	@行情初始化	--	开始初始化【" + market + "】市场");
                
                hqState.setInit(false);
                initHQSystem(market);
                hqState.setCurrentDate(initData);
                hqState.setInit(true);
                
                logger.warn("	--	@行情初始化	--	初始化【" + market + "】市场完成，行情日期为 : " + initData);
            }
        }
    }
    
    /**
     * @描述: 初始化行情数据
     * @作者: 王嵊俊
     * @创建日期: 2016年5月12日 上午10:59:55
     * @param initDate
     * @param marketType
     */
    private void initHQSystem(String market)
    {
        if ( ServerConfig.HQ_MARKET_HS.equals(market) )
        {
            GateWayDao.queryAllStock();
            GateWayDao.queryAllBaseInfo();
        }
        else if ( ServerConfig.HQ_MARKET_HK.equals(market) )
        {
            GateWayDao.queryHKAllStock();
        }
        
        IStockInfoService stockInfoService = BeanFactory.getBean(Key.StockInfoService);
        stockInfoService.insertOrUpdate(ServerCache.getHQCache());
    }
    
    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        try
        {
            MCState state = null;
            state = GateWayDao.queryMCState();
            
            if ( state != null )
            {
                String mcInitDate = state.getInitDate();// 初始化时间
                String hkMcInitDate = state.getHkInitDate();// 港股通初始化日期
                long hkSourceTime = state.getHkdbfTime();
                long sourceTime = state.getDbfTime();
                
                HQState.get(ServerConfig.HQ_MARKET_HS).setSourceTime(sourceTime);
                HQState.get(ServerConfig.HQ_MARKET_HK).setSourceTime(hkSourceTime);
                
                checkHQInit(mcInitDate, ServerConfig.HQ_MARKET_HS);
                checkHQInit(hkMcInitDate, ServerConfig.HQ_MARKET_HK);
            }
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
    }
}
