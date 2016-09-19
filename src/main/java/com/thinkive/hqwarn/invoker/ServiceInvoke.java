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
 * ����: ��Ȩ: Copyright (c) 2007 ��˾: ˼�ϿƼ� ����: ����� �汾: 1.0 ��������: 2009-1-9 ����ʱ��:
 * 17:29:53
 */
public class ServiceInvoke
{
    private static final Log               logger      = LogFactory.getLog("Server");
                                                       
    private static Map<Integer, IFunction> functionMap = loadFuncnoClassInfo();
                                                       
    /**
     * @����: �ַ�����
     * @����: ���ӿ�
     * @��������: 2016��5��5�� ����2:08:45
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
     * @����: װ�ع��ܽӿ�����Ϣ
     * @����: ���ӿ�
     * @��������: 2016��4��14�� ����4:20:28
     */
    public static Map<Integer, IFunction> loadFuncnoClassInfo()
    {
        Map<Integer, IFunction> funcMap = new HashMap<>();
        
        loadFuncnoClassByConfig(funcMap);
        loadFuncnoClassByDefaultPath(funcMap);
        
        return funcMap;
    }
    
    /**
     * @����: ��Ĭ��·���ж�ȡ�ӿڴ�����
     * @����: ���ӿ�
     * @��������: 2016��5��5�� ����2:30:53
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
     * @����: ͨ�������ļ�������Ӧ�Ľӿ�ִ���ࣨ���ȼ�����Ĭ��·����
     * @����: ���ӿ�
     * @��������: 2016��4��14�� ����7:17:14
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
                logger.warn("	--	@ϵͳ��ʼ��	--	���ô��� , ���Ϸ��������� : " + key);
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
                        "	--	@ϵͳ��ʼ��	--	���ô��� , ���Ϸ��������� : [ name = " + key + " ],[ value = " + map.get(key) + " ]",
                        e);
                        
            }
            catch (InstantiationException e)
            {
                // TODO Auto-generated catch block
                
                logger.warn(
                        "	--	@ϵͳ��ʼ��	--	���ô��� , ���Ϸ��������� : [ name = " + key + " ],[ value = " + map.get(key) + " ]",
                        e);
                        
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                
                logger.warn(
                        "	--	@ϵͳ��ʼ��	--	���ô��� , ���Ϸ��������� : [ name = " + key + " ],[ value = " + map.get(key) + " ]",
                        e);
            }
            
        }
        
    }
    
}
