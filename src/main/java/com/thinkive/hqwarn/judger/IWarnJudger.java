package com.thinkive.hqwarn.judger;

import java.util.List;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.bean.WarnInfo;

/**
 * @描述: 提醒触发判断
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年5月12日 下午3:07:49
 */
public interface IWarnJudger
{
    public List<WarnInfo> judger(Stock stock);
    
    public String getMessageContent(Stock stock, WarnInfo warnInfo);
}
