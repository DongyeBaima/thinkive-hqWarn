package com.thinkive.hqwarn.cache;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.bean.WarnPushInfo;

/**
 * @描述: 队列管理
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月13日 下午2:29:06
 */
public class QueueManager
{
    public static final BlockingQueue<Stock>        hqQueue       = new LinkedBlockingQueue<Stock>();        // 实时行情推送, 线程安全
                                                                  
    public static final BlockingQueue<WarnPushInfo> warnPushQueue = new LinkedBlockingQueue<WarnPushInfo>(); // 提醒推送
                                                                  
    public static final BlockingQueue<WarnInfo>     dbUpdateQueue = new LinkedBlockingQueue<WarnInfo>();     // 数据库更新队列
                                                                  
}
