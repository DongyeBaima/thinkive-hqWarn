package com.thinkive.base.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyLog
{
    public static final Log serverLog = LogFactory.getLog("Server");
                                      
    public static final Log appLog    = LogFactory.getLog("AppMessage");
                                      
    public static final Log pushLog   = LogFactory.getLog("PushMessage");
                                      
}
