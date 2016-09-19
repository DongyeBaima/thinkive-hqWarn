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
 * @����: httpЭ����������ͻ���
 * @��Ȩ: Copyright (c) 2015
 * @��˾: Thinkive
 * @����: ���ӿ�
 * @��������: 2015��11��5�� ����7:09:41
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
            
            // ���� BufferedReader����������ȡURL����Ӧ
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
     * @����: ������������ַ���
     * @����: ���ӿ�
     * @��������: 2016��5��8�� ����10:56:41
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
     * @����: ��ȡHttpURLConnection
     * @����: ���ӿ�
     * @��������: 2015��11��5�� ����7:59:12
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
            
            // �������Ӳ���
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
        }
        catch (ProtocolException e)
        {
            // TODO Auto-generated catch block
            logger.warn("	-- @HTTP����  --	����ʧ�ܣ�[host : " + host + "]", e);
            
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            logger.warn("	-- @HTTP����  --	����ʧ�ܣ�[host : " + host + "]", e);
        }
        
        return connection;
    }
}
