package com.thinkive.hqwarn.judger;

import java.util.List;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * @����: ���Ѵ����ж�
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��5��12�� ����3:07:49
 */
public interface IWarnJudger
{
    public List<WarnInfo> judger(Stock stock);
    
    public String getMessageContent(Stock stock, WarnInfo warnInfo);
}
