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
                back.put("errorInfo", "������ʽ����");
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
     * @����: ����������
     * @����: ���ӿ�
     * @��������: 2016��5��18�� ����11:10:01
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
     * @��������ʼ������ ����MAP
     * @���ߣ���֪֮ @ʱ�䣺2013-1-16 ����5:23:28
     * @param request
     * @param params
     * @throws UnsupportedEncodingException
     */
    private void initRequest(RequestImpl request, Map<String, Object> params) throws UnsupportedEncodingException
    {
        try
        {
            // -- json ת����� int --> long , float --> double
            
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
            logger.debug("����Ĳ���version��funcno���󣡣���������������");
        }
        
        for (String key : params.keySet())
        {
            request.addParameter(key, params.get(key));
        }
        
    }
    
    /**
     * ���ͳɹ���Http��Ӧ
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
     * @����: �������������
     * @����: ���ӿ�
     * @��������: 2016��6��21�� ����5:12:11
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
     * @����: ����Ʊ�� �ͻ��з�
     * @����: ���ӿ�
     * @��������: 2016��6��21�� ����5:53:42
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
