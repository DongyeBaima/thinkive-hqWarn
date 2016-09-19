package com.thinkive.base.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyLogger
{
    private static Log                   logger    = LogFactory.getLog("Server");
                                                   
    private static final String          rootPath  = "./testLog/";
                                                   
    private static Map<String, MyLogger> loggerMap = new ConcurrentHashMap<String, MyLogger>();
                                                   
    public static synchronized MyLogger getLogger(String fileName)
    {
        MyLogger logger = loggerMap.get(fileName);
        
        if ( logger == null )
        {
            logger = new MyLogger(fileName);
            loggerMap.put(fileName, logger);
        }
        
        return logger;
    }
    
    // -- -- -- -- -- -- -- -- -- -- 【 分 -- 隔 -- 线 】-- -- -- -- -- -- -- --
    
    private final String filePath;
                         
    private final String fileName;
                         
    private MyLogger(String fileName)
    {
        this.fileName = fileName;
        filePath = getPath();
        CreatFile();
    }
    
    private final void CreatFile()
    {
        
        FileHelper.createNewFile(filePath);
    }
    
    /**
     * @描述: 写日志
     * @作者: 王嵊俊
     * @创建日期: 2015年11月10日 下午5:36:55
     * @param o
     */
    public final void write(Object o, boolean type)
    {
        String time = getTime();
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(filePath, type));
            writer.write(time + "  -  " + o + "\r\n");
            writer.flush();
            
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            logger.warn("	--@写文件失败--	" + filePath, e);
            loggerMap.remove(fileName);
            
        }
        finally
        {
            if ( writer != null )
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
    
    /**
     * @描述: 写日志
     * @作者: 王嵊俊
     * @创建日期: 2015年11月10日 下午5:36:55
     * @param o
     */
    public final void write(Object o)
    {
        write(o, true);
    }
    
    /**
     * @描述: 清空此日志文件
     * @作者: 王嵊俊
     * @创建日期: 2015年11月10日 下午5:37:18
     */
    public final void clear()
    {
        write("", false);
    }
    
    /**
     * @描述: 获取文件路径
     * @作者: 王嵊俊
     * @创建日期: 2015年11月10日 下午5:00:07
     * @param
     * @return
     */
    private final String getPath()
    {
        File dir = new File(rootPath);
        if ( !dir.exists() )
            dir.mkdir();
        String date = DateHelper.formatDate(new Date(), "yyyyMMdd");
        return rootPath + date + "/" + fileName + ".log";
    }
    
    // -- -- -- -- -- -- -- -- -- -- 【 分 -- 隔 -- 线 】-- -- -- -- -- -- -- --
    
    /**
     * @描述: 清空所有测试日志文件
     * @作者: 王嵊俊
     * @创建日期: 2015年11月13日 上午9:45:38
     */
    public static void ClearAll()
    {
        File testLogDir = new File(rootPath);
        
        if ( testLogDir.isDirectory() )
        {
            File files[] = testLogDir.listFiles();
            for (File file : files)
            {
                file.delete();
            }
        }
    }
    
    /**
     * @描述: 获取当前时间
     * @作者: 王嵊俊
     * @创建日期: 2015年11月10日 下午5:23:21
     */
    public static String getTime()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(System.currentTimeMillis());
    }
    
}
