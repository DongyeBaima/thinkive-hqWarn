package com.thinkive.warn.factory;

import com.thinkive.base.jdbc.JdbcTemplate;
import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.warn.service.IDBService;
import com.thinkive.warn.service.IServerStateService;
import com.thinkive.warn.service.IStateChangeService;
import com.thinkive.warn.service.IStockInfoService;
import com.thinkive.warn.service.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述: 对象工厂，模拟spring
 * @版权: Copyright (c) 2016
 * @公司: Thinkive
 * @作者: 王嵊俊
 * @创建日期: 2016年8月4日 下午1:43:27
 */
public class BeanFactory
{
    private static Map<String, Object> beanMap = new HashMap<>();

    private static boolean isInit = false;

    /**
     * @描述: 初始化Bean工厂
     * @作者: 王嵊俊
     * @创建日期: 2016年8月4日 下午1:50:45
     */
    public static void init()
    {
        synchronized (BeanFactory.class)
        {
            if (isInit)
            {
                return;
            }
            isInit = true;

            try
            {
                JdbcTemplate template = new JdbcTemplate();
                beanMap.put(Key.DBTemplate.toString(), template);

                ServerState state = new ServerState(ServerConfig.SERVER_ID, ServerConfig.SERVER_ZONEID,
                                                    ServerConfig.SERVER_PRIORITY);
                beanMap.put(Key.ServerState.toString(), state);

                //                IOptionStockService optionStockService = new OptionStockService();
                //                beanMap.put(Key.OptionStockService, optionStockService);
                //
                //                IUserService userService = new UserService();
                //                beanMap.put(Key.UserService, userService);
                //
                //                IStockWarnService stockWarnService = new StockWarnService();
                //                beanMap.put(Key.StockWarnService, stockWarnService);

                IStockInfoService stockInfoService = new StockInfoService();
                beanMap.put(Key.StockInfoService.toString(), stockInfoService);

                IServerStateService serverStateService = new ServerStateService();
                beanMap.put(Key.ServerStateService.toString(), serverStateService);

                IStateChangeService stateChangeService = new StateChangeService();
                beanMap.put(Key.StateChangeService.toString(), stateChangeService);

                IDBService<WarnInfo> warnInfoService = new NStockWarnService();
                beanMap.put(Key.NWarnService.toString(), warnInfoService);

                IDBService<UserInfo> userInfoService = new NUserService();
                beanMap.put(Key.NUserService.toString(), userInfoService);

            } catch (Exception e)
            {
                MyLog.serverLog.warn("", e);
                isInit = false;
            }
        }
    }

    /**
     * @param key
     * @return
     * @描述: 根据Key获取bean对象
     * @作者: 王嵊俊
     * @创建日期: 2016年8月4日 下午1:46:43
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Key key)
    {
        return getBean(key.name());
    }

    public static <T> T getBean(String key)
    {
        if (!isInit)
        {
            init();
        }

        Object o = beanMap.get(key);
        if (o == null)
        {
            if (!isInit)
            {
                throw new IllegalStateException(" BeanFactory 没有初始化！");
            }
            else
            {
                throw new IllegalStateException(" 未找到 bean ： " + key);
            }
        }

        return (T) o;
    }

    public static void addBean(String key, Object bean)
    {
        beanMap.put(key,bean);
    }

    /**
     * @描述: beanMap中包含bean对象的Key
     * @版权: Copyright (c) 2016
     * @公司: Thinkive
     * @作者: 王嵊俊
     * @创建日期: 2016年8月4日 下午2:21:16
     */
    public enum Key
    {
        DBTemplate, OptionStockService, UserService, StockWarnService, ServerStateService, ServerState, StateChangeService, StockInfoService, NWarnService, NUserService
    }

}
