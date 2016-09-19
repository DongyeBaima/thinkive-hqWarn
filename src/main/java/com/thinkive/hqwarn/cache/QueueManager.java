package com.thinkive.hqwarn.cache;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.bean.WarnPushInfo;

/**
 * @����: ���й���
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��13�� ����2:29:06
 */
public class QueueManager
{
    public static final BlockingQueue<Stock>        hqQueue       = new LinkedBlockingQueue<Stock>();        // ʵʱ��������, �̰߳�ȫ
                                                                  
    public static final BlockingQueue<WarnPushInfo> warnPushQueue = new LinkedBlockingQueue<WarnPushInfo>(); // ��������
                                                                  
    public static final BlockingQueue<WarnInfo>     dbUpdateQueue = new LinkedBlockingQueue<WarnInfo>();     // ���ݿ���¶���
                                                                  
}
