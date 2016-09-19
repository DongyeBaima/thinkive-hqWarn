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
 * @����: �����ʼ���������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��12�� ����11:30:59
 */
public class HQInitTask implements Task
{
    private static Log logger = LogFactory.getLog("Server");
    
    /**
     * @����:  �жϸ��г��Ƿ���Ҫ��ʼ��,����Ҫ���ʼ������
     * @����: ���ӿ�
     * @��������: 2016��5��12�� ����11:14:57
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
                logger.warn("	--	@�����ʼ��	--	��ʼ��ʼ����" + market + "���г�");
                
                hqState.setInit(false);
                initHQSystem(market);
                hqState.setCurrentDate(initData);
                hqState.setInit(true);
                
                logger.warn("	--	@�����ʼ��	--	��ʼ����" + market + "���г���ɣ���������Ϊ : " + initData);
            }
        }
    }
    
    /**
     * @����: ��ʼ����������
     * @����: ���ӿ�
     * @��������: 2016��5��12�� ����10:59:55
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
                String mcInitDate = state.getInitDate();// ��ʼ��ʱ��
                String hkMcInitDate = state.getHkInitDate();// �۹�ͨ��ʼ������
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
