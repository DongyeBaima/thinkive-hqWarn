package Test;

import com.thinkive.base.util.MyLog;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.cache.QueueManager;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.util.HQHelper;

public class ZMJPushTest
{

    public static void startPushTest()
    {
        try
        {
            // -- -- ����1���� �ȴ�ϵͳ��ʼ�����
            Thread.sleep(10000);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        new Thread(new testRunnable()).start();
    }

    static class testRunnable implements Runnable
    {

        @SuppressWarnings("unused")
        @Override
        public void run()
        {
            // TODO Auto-generated method stub

            while (true)
            {
                try
                {
                    String stockCode = "SH000001";// 8���ֽ�
                    String market = stockCode.substring(0, 2);
                    String code = stockCode.substring(2);

                    Stock stock = ServerCache.getStock(stockCode);

                    if ( stock == null )
                    {
                        MyLog.serverLog.warn("	--	@��������	--	�����в����ڸù�Ʊ��Ϣ��" + stockCode);
                        return;
                    }

                    stock.setNow(3000);
                    stock.setSourceTime(System.currentTimeMillis());

                    HQHelper.calculate(stockCode);

                    QueueManager.hqQueue.put(stock);

                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    MyLog.serverLog.warn("", e);
                }

                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }
}
