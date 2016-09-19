package com.thinkive.hqwarn.invoker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.thinkive.base.config.Configuration;
import com.thinkive.base.util.ClassHelper;
import com.thinkive.base.util.MyStringUtil;
import com.thinkive.hqwarn.function.AbstractFunction;
import com.thinkive.hqwarn.function.IFunction;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**
 * 描述: 版权: Copyright (c) 2007 公司: 思迪科技 作者: 易庆锋 版本: 1.0 创建日期: 2009-1-9 创建时间:
 * 17:29:53
 */
public class ServiceInvoke
{
    private static final Log               logger      = LogFactory.getLog("Server");
                                                       
    private static Map<Integer, IFunction> functionMap = loadFuncnoClassInfo();
                                                       
    /**
     * @描述: 分发请求
     * @作者: 王嵊俊
     * @创建日期: 2016年5月5日 下午2:08:45
     * @param request
     * @param response
     * @throws InvokeException
     */
    public static void Invoke(Request request, Response response) throws InvokeException
    {
        try
        {
            if ( request.getFuncNo() != 0 )
            {
                int funcNo = request.getFuncNo();
                
                IFunction function = functionMap.get(funcNo);
                
                if ( function != null )
                {
                    function.service(request, response);
                }
                else
                {
                    throw new Exception();
                }
            }
        }
        catch (Exception ex)
        {
            logger.warn("Invoke Function Failed[funcno=" + request.getFuncNo() + "]", ex);
            
            throw new InvokeException("Invoke Function Failed[funcno=" + request.getFuncNo() + "]", ex);
        }
    }
    
    /**
     * @描述: 装载功能接口类信息
     * @作者: 王嵊俊
     * @创建日期: 2016年4月14日 下午4:20:28
     */
    public static Map<Integer, IFunction> loadFuncnoClassInfo()
    {
        Map<Integer, IFunction> funcMap = new HashMap<>();
        
        loadFuncnoClassByConfig(funcMap);
        loadFuncnoClassByDefaultPath(funcMap);
        
        return funcMap;
    }
    
    /**
     * @描述: 在默认路径中读取接口处理类
     * @作者: 王嵊俊
     * @创建日期: 2016年5月5日 下午2:30:53
     * @param funcMap
     */
    @SuppressWarnings("rawtypes")
    private static void loadFuncnoClassByDefaultPath(Map<Integer, IFunction> funcMap)
    {
        List<Class> classList = ClassHelper.getAllClassByFather(AbstractFunction.class);
        
        if ( classList == null )
        {
            return;
        }
        
        String className = null;
        for (Class c : classList)
        {
            try
            {
                className = c.getSimpleName();
                
                if ( className == null )
                {
                    continue;
                }
                
                if ( className.startsWith("Function") )
                {
                    className = className.substring(8);
                    
                    int funcNo = MyStringUtil.strToInt(className);
                    
                    if ( funcNo == 0 || funcMap.get(funcNo) != null )
                    {
                        continue;
                    }
                    
                    funcMap.put(funcNo, (IFunction) c.newInstance());
                    
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
            }
        }
    }
    
    /**
     * @描述: 通过配置文件加载相应的接口执行类（优先级高于默认路径）
     * @作者: 王嵊俊
     * @创建日期: 2016年4月14日 下午7:17:14
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void loadFuncnoClassByConfig(Map<Integer, IFunction> funcMap)
    {
        Map<String, String> map = Configuration.getItems();
        
        for (String key : map.keySet())
        {
            if ( !key.startsWith("Function.") )
            {
                continue;
            }
            
            String func = key.substring(9);
            int funcNo = MyStringUtil.strToInt(func);
            
            if ( funcNo == 0 )
            {
                logger.warn("	--	@系统初始化	--	配置错误 , 不合法的配置项 : " + key);
                continue;
            }
            
            try
            {
                Class c = Class.forName(map.get(key));
                
                funcMap.put(funcNo, (IFunction) c.newInstance());
                
            }
            catch (ClassNotFoundException e)
            {
                // TODO Auto-generated catch block
                
                logger.warn(
                        "	--	@系统初始化	--	配置错误 , 不合法的配置项 : [ name = " + key + " ],[ value = " + map.get(key) + " ]",
                        e);
                        
            }
            catch (InstantiationException e)
            {
                // TODO Auto-generated catch block
                
                logger.warn(
                        "	--	@系统初始化	--	配置错误 , 不合法的配置项 : [ name = " + key + " ],[ value = " + map.get(key) + " ]",
                        e);
                        
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                
                logger.warn(
                        "	--	@系统初始化	--	配置错误 , 不合法的配置项 : [ name = " + key + " ],[ value = " + map.get(key) + " ]",
                        e);
            }
            
        }
        
    }
    
}
