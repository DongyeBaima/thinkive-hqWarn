package com.thinkive.web.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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
import com.thinkive.hqwarn.util.ServerConfig;
import com.thinkive.web.bean.RequestImpl;
import com.thinkive.web.bean.ResponseImpl;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet
{
    private static Log logger = LogFactory.getLog("Server");
    
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestImpl req = new RequestImpl();
        ResponseImpl resp = new ResponseImpl();
        
        if ( !ServerConfig.IS_DEBUG )
        {
            return;
        }
        
        try
        {
            Map<String, Object> paramMap = processRequest(request, response);
            
            if ( paramMap != null )
            {
                initRequest(req, paramMap);
                ServiceInvoke.Invoke(req, resp);
                sendSuccessHttpResponse(request, response, resp.getResult());
                
            }
            else
            {
                Map<String, Object> back = new HashMap<>();
                back.put("errorNo", -999996);
                back.put("errorInfo", "参数格式错误");
                sendSuccessHttpResponse(request, response, back);
            }
            
        }
        catch (InvokeException ex)
        {
            Map<String, Object> back = new HashMap<>();
            back.put("errorNo", -999997);
            back.put("errorInfo", ex.getMessage());
            sendSuccessHttpResponse(request, response, back);
        }
        catch (Exception ex)
        {
            Map<String, Object> back = new HashMap<>();
            back.put("errorNo", -999997);
            back.put("errorInfo", ex.getMessage());
            sendSuccessHttpResponse(request, response, back);
            
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
            Map<String, Object> paramMap = (Map<String, Object>) JSON.parse(params);
            
            return paramMap;
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
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
    private void sendSuccessHttpResponse(HttpServletRequest request, HttpServletResponse httpResponse, Object response)
    {
        PrintWriter writer = null;
        try
        {
            httpResponse.setCharacterEncoding(Library.getEncoding());
            httpResponse.setContentType("application/json;charset=UTF-8");
            
            writer = httpResponse.getWriter();
            
            StringBuilder sb = new StringBuilder();
            
            processResponse(sb, response, 0);
            
            writer.print(sb.toString());
            writer.flush();
            
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
    
    protected final String LINE_FLAG  = "\r\n";
                                      
    protected final String TABLE_FLAG = "\t";
                                      
    /**
     * @描述: 解析结果集，用
     * @作者: 王嵊俊
     * @创建日期: 2016年6月21日 下午5:12:11
     * @return
     */
    @SuppressWarnings("unchecked")
    private void processResponse(StringBuilder sb, Object response, int tableNum)
    {
        if ( response instanceof Map )
        {
            Map<Object, Object> map = (Map<Object, Object>) response;
            for (Object o : map.keySet())
            {
                processResponse(sb, o, tableNum);
                processResponse(sb, map.get(o), tableNum + 1);
            }
        }
        else if ( response instanceof List )
        {
            List<Object> list = (List<Object>) response;
            
            for (Object o : list)
            {
                processResponse(sb, o, tableNum);
            }
        }
        else
        {
            appendStringFlag(sb, tableNum);
            
            if ( response != null && response.getClass().isArray() )
            {
                Object array[] = (Object[]) response;
                
                for (Object o : array)
                {
                    sb.append(o + TABLE_FLAG);
                }
                
            }
            else
            {
                sb.append(response);
            }
        }
    }
    
    /**
     * @描述: 添加制表符 和换行符
     * @作者: 王嵊俊
     * @创建日期: 2016年6月21日 下午5:53:42
     * @param sb
     * @param tableNum
     */
    private void appendStringFlag(StringBuilder sb, int tableNum)
    {
        sb.append(LINE_FLAG);
        
        for (int i = 0; i < tableNum; i++)
        {
            sb.append(TABLE_FLAG);
        }
    }
}
