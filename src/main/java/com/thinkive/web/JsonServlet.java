package com.thinkive.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.util.ajax.JSON;

import com.thinkive.base.util.Library;
import com.thinkive.hqwarn.invoker.InvokeException;
import com.thinkive.hqwarn.invoker.ServiceInvoke;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.web.bean.RequestImpl;
import com.thinkive.web.bean.Response;
import com.thinkive.web.bean.ResponseImpl;

/**
 * @描述: 用于处理HTTP协议请求
 * @版权: Copyright (c) 2012
 * @公司: 思迪科技
 * @作者: 岳知之
 * @版本: 1.0
 * @创建日期: 2012-4-21
 * @创建时间: 下午2:56:11
 */
@SuppressWarnings("serial")
public class JsonServlet extends HttpServlet
{
    private static Log logger    = LogFactory.getLog("Server");
                                 
    private static Log appLogger = LogFactory.getLog("AppMessage");
                                 
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestImpl req = new RequestImpl();
        ResponseImpl resp = new ResponseImpl();
        
        try
        {
            Map<String, Object> paramMap = processRequest(request, response);
            
            if ( paramMap != null )
            {
                initRequest(req, paramMap);
                resp.setFuncNo(req.getFuncNo());
                ServiceInvoke.Invoke(req, resp);
                sendSuccessHttpResponse(request, response, resp);
            }
            else
            {
                resp.setErrorNo( -999996);
                resp.setErrorInfo("参数格式错误");
                sendSuccessHttpResponse(request, response, resp);
            }
        }
        catch (InvokeException ex)
        {
            resp.setErrorNo( -999997);
            resp.setErrorInfo(ex.getMessage());
            sendSuccessHttpResponse(request, response, resp);
        }
        catch (Exception ex)
        {
            resp.setErrorNo( -999997);
            resp.setErrorInfo(ex.getMessage());
            sendSuccessHttpResponse(request, response, resp);
            
            logger.warn("", ex);
        }
        finally
        {
            resp.clear();
        }
    }
    
    /**
     * @描述: 解析出参数
     * @作者: 王嵊俊
     * @创建日期: 2016年5月18日 上午11:10:01
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> processRequest(HttpServletRequest request, HttpServletResponse response)
    {
        String params = request.getParameter("params");
        try
        {
            if ( params.indexOf("%") >= 0 )
            {
                params = URLDecoder.decode(params, "utf-8");
            }
            Map<String, Object> paramMap = (Map<String, Object>) JSON.parse(params);
            return paramMap;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * @描述：初始化请求 根据MAP
     * @作者：岳知之 @时间：2013-1-16 下午5:23:28
     * @param request
     * @param params
     * @throws UnsupportedEncodingException
     */
    private void initRequest(RequestImpl request, Map<String, Object> params) throws UnsupportedEncodingException
    {
        try
        {
            // -- json 转换会把 int --> long , float --> double
            
            Long funcNo = (Long) params.get("funcno");
            Long versionNo = (Long) params.get("version");
            
            if ( funcNo != null )
            {
                request.setFuncNo((int) funcNo.longValue());
            }
            
            if ( versionNo != null )
            {
                request.setVersionNo((int) versionNo.longValue());
            }
        }
        catch (NumberFormatException e)
        {
            logger.debug("输入的参数version或funcno有误！！！！！！！！！");
        }
        
        for (String key : params.keySet())
        {
            request.addParameter(key, params.get(key));
        }
    }
    
    /**
     * 发送成功的Http响应
     * 
     * @param httpResponse
     * @param request
     * @param response
     */
    private void sendSuccessHttpResponse(HttpServletRequest request, HttpServletResponse httpResponse,
            Response response)
    {
        PrintWriter writer = null;
        try
        {
            httpResponse.setCharacterEncoding(Library.getEncoding());
            httpResponse.setContentType("application/json;charset=UTF-8");
            
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("results", response.getResult());
            resultMap.put("errorNo", response.getErrorNo());
            resultMap.put("errorInfo", response.getErrorInfo());
            
            writer = httpResponse.getWriter();
            String json = JSON.toString(resultMap);
            
            writer.print(json);
            writer.flush();
            
            if ( !(response.getFuncNo() == FuncHelper.HEART_FUNCNO && response.getErrorNo() == FuncHelper.SUCCESS) )
            {
                String params = request.getParameter("params");
                if ( params.indexOf("%") >= 0 )
                {
                    params = URLDecoder.decode(params, "utf-8");
                }
                appLogger.debug("	--	@HOST	--	" + request.getRemoteHost());
                appLogger.debug("	--	@请求	--	" + params);
                appLogger.debug("	--	@响应	--	" + json);
                appLogger.debug("");
            }
        }
        catch (Exception e)
        {
            logger.warn("", e);
        }
        finally
        {
            if ( writer != null )
            {
                writer.close();
                writer = null;
            }
        }
    }
    
}
