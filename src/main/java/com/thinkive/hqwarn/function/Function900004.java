package com.thinkive.hqwarn.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.cache.QueueManager;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.Response;

/**
 * @����: ��������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��6��21�� ����6:16:14
 */
public class Function900004 extends AbstractFunction
{
    
    @Override
    protected int processParams(Request request, Map<String, Object> params)
    {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    protected void service(Map<String, Object> params, Response response) throws Exception
    {
        // TODO Auto-generated method stub
        List<Stock> list = new ArrayList<>();
        for (Stock stock : QueueManager.hqQueue)
        {
            list.add(stock);
        }
        
        response.write(list);
    }
    
}
