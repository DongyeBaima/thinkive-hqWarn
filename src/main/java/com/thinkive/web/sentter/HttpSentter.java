package com.thinkive.web.sentter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @描述: http协议数据请求客户端
 * @版权: Copyright (c) 2015
 * @公司: Thinkive
 * @作者: 王嵊俊
 * @创建日期: 2015年11月5日 下午7:09:41
 */
public class HttpSentter
{
    
    private static final Log logger = LogFactory.getLog("Server");
                                    
    protected String         host;
                             
    protected String         params;
                             
    protected HttpSentter()
    {
    }
    
    public HttpSentter(String host, Map<String, ?> params)
    {
        this.host = host;
        this.params = processParams(params);
    }
    
    public HttpSentter(String host, String params)
    {
        this.host = host;
        this.params = params;
    }
    
    public String queryData() throws IOException
    {
        HttpURLConnection connection = getConnection();
        
        if ( connection == null )
        {
            return null;
        }
        
        BufferedReader in = null;
        try
        {
            connection.connect();
            
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            String result = "";
            String line;
            
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
            
            return result;
            
        }
        finally
        {
            if ( in != null )
            {
                in.close();
                in = null;
            }
            if ( connection != null )
            {
                connection.disconnect();
                connection = null;
            }
        }
    }
    
    /**
     * @描述: 将参数处理成字符串
     * @作者: 王嵊俊
     * @创建日期: 2016年5月8日 下午10:56:41
     * @return
     */
    protected String processParams(Map<String, ?> paramMap)
    {
        if ( paramMap == null || paramMap.isEmpty() )
        {
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (String key : paramMap.keySet())
        {
            params.append(key + "=" + paramMap.get(key));
            params.append("&");
        }
        
        params.deleteCharAt(params.length() - 1);
        
        return params.toString();
    }
    
    /**
     * @描述: 获取HttpURLConnection
     * @作者: 王嵊俊
     * @创建日期: 2015年11月5日 下午7:59:12
     * @return
     * @throws UnsupportedEncodingException 
     */
    private final HttpURLConnection getConnection() throws UnsupportedEncodingException
    {
        if ( host == null )
        {
            throw new IllegalStateException("	URL is null !");
        }
        
        if ( params != null )
        {
            host = host + "?" + params;
        }
        
        HttpURLConnection connection = null;
        try
        {
            URL url = new URL(host);
            
            connection = (HttpURLConnection) url.openConnection();
            
            // 设置连接参数
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
        }
        catch (ProtocolException e)
        {
            // TODO Auto-generated catch block
            logger.warn("	-- @HTTP服务  --	连接失败：[host : " + host + "]", e);
            
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            logger.warn("	-- @HTTP服务  --	连接失败：[host : " + host + "]", e);
        }
        
        return connection;
    }
}
