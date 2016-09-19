package com.thinkive.hqwarn.netty;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import com.thinkive.base.util.ByteStrHelper;
import com.thinkive.base.util.Library;
import com.thinkive.base.util.MyLog;
import com.thinkive.base.util.MyStringUtil;
import com.thinkive.hq.bean.Stock;
import com.thinkive.hqwarn.cache.HQState;
import com.thinkive.hqwarn.cache.NetConnManager;
import com.thinkive.hqwarn.cache.ServerCache;
import com.thinkive.hqwarn.netty.codec.GateWayHandler;
import com.thinkive.hqwarn.netty.codec.MessageCodec;
import com.thinkive.hqwarn.netty.message.Message;
import com.thinkive.market.bean.MCState;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @����: ��������,�������������������
 * @��Ȩ: Copyright (c) 2016 
 * @��˾: Thinkive 
 * @����: ���ӿ�
 * @��������: 2016��3��18�� ����10:31:16
 */
public final class GateWayDao
{
    private static int      MAX_PACKET_SIZE = 6 * 1024 * 1024;
                                            
    protected static String SOH             = new String(new byte[] { 1 });
                                            
    protected static String STX             = new String(new byte[] { 2 });
                                            
    private static boolean sendRequest(final Message msg, InetSocketAddress address)
    {
        if ( address == null )
        {
            address = NetConnManager.GATEWAY.getConnAddress();
        }
        
        if ( address == null )
        {
            return false;
        }
        
        // ���÷���˵�NIO�߳���
        EventLoopGroup workerGroup = null;
        try
        {
            workerGroup = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(workerGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, false)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        public void initChannel(SocketChannel ch)
                        {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN,
                                    MAX_PACKET_SIZE, 14, 4, 0, 0, false));
                            ch.pipeline().addLast(new MessageCodec());
                            ch.pipeline().addLast(new GateWayHandler(msg));
                        }
                    });
                    
            // �󶨶˿ڣ�ͬ���ȴ��ɹ�
            ChannelFuture f = b.connect(address).sync();
            
            // �ȴ�����˼����˿ڹر�
            f.channel().closeFuture().sync();
            
            return true;
            
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        finally
        {
            // �����˳����ͷ��̳߳���Դ
            workerGroup.shutdownGracefully();
        }
    }
    
    /**
     * @����: ������ȡ����������
     * @����: ���ӿ�
     * @��������: 2016��5��11�� ����4:51:52
     * @param msg
     * @return
     */
    public static byte[] queryData(Message msg, InetSocketAddress address)
    {
        sendRequest(msg, address);
        
        byte data[] = msg.getData();
        
        return data;
    }
    
    public static byte[] queryData(Message msg)
    {
        return queryData(msg, null);
    }
    
    /**
     * @����: �����ظ��»�����������
     * @����: ���ӿ�
     * @��������: 2016��5��11�� ����4:51:24
     */
    @SuppressWarnings("unused")
    public static void queryAllStock()
    {
        try
        {
            Message message = new Message();
            message.setFuncNo(10004);
            
            byte data[] = queryData(message);
            
            if ( data != null )
            {
                int STRUCT_LENGTH = 182;
                ByteBuffer dataBuffer = ByteBuffer.wrap(data);
                dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
                int size = data.length / STRUCT_LENGTH;
                
                for (int i = 0; i < size; i++)
                {
                    
                    short serno = dataBuffer.getShort(); // ���
                    String code = ByteStrHelper.getString(dataBuffer, 7); // ����
                    
                    String name = ByteStrHelper.getString(dataBuffer, 9); // ����
                    
                    int stktype = dataBuffer.getInt(); // ��Ʊ����
                    int curdate = dataBuffer.getInt(); // ��ǰ����
                    
                    String market = ByteStrHelper.getString(dataBuffer, 3);// �г�
                    String pyname = ByteStrHelper.getString(dataBuffer, 5);// ƴ������
                    short minutes = dataBuffer.getShort();
                    
                    // ��5�ۣ���5�� ...
                    float sellprice5 = dataBuffer.getFloat();
                    float sellvol5 = dataBuffer.getFloat();
                    float sellprice4 = dataBuffer.getFloat();
                    float sellvol4 = dataBuffer.getFloat();
                    float sellprice3 = dataBuffer.getFloat();
                    float sellvol3 = dataBuffer.getFloat();
                    float sellprice2 = dataBuffer.getFloat();
                    float sellvol2 = dataBuffer.getFloat();
                    float sellprice1 = dataBuffer.getFloat();
                    float sellvol1 = dataBuffer.getFloat();
                    // ��1�ۣ���1��...
                    float buyprice1 = dataBuffer.getFloat();
                    float buyvol1 = dataBuffer.getFloat();
                    float buyprice2 = dataBuffer.getFloat();
                    float buyvol2 = dataBuffer.getFloat();
                    float buyprice3 = dataBuffer.getFloat();
                    float buyvol3 = dataBuffer.getFloat();
                    float buyprice4 = dataBuffer.getFloat();
                    float buyvol4 = dataBuffer.getFloat();
                    float buyprice5 = dataBuffer.getFloat();
                    float buyvol5 = dataBuffer.getFloat();
                    // ����,��,��ߣ����,�ּ�,�ܳɽ������ܳɽ������̣�����,�ǵ�,�ǵ��ٷֱ�
                    float yesterday = dataBuffer.getFloat();
                    float open = dataBuffer.getFloat();
                    float high = dataBuffer.getFloat();
                    float low = dataBuffer.getFloat();
                    float now = dataBuffer.getFloat();
                    float volume = dataBuffer.getFloat();
                    float amount = dataBuffer.getFloat();
                    float outside = dataBuffer.getFloat();
                    float inside = dataBuffer.getFloat();
                    float up = dataBuffer.getFloat();
                    float uppercent = dataBuffer.getFloat();
                    float thedeal = dataBuffer.getFloat();
                    float volrate = dataBuffer.getFloat();
                    float min5change = dataBuffer.getFloat();
                    
                    String isSuspended = ByteStrHelper.getString(dataBuffer, 1); // ͣ�Ʊ�ʶ
                    int marginTradMark = MyStringUtil.strToInt(ByteStrHelper.getString(dataBuffer, 1)); // ������ȯ
                    String transferType = ByteStrHelper.getString(dataBuffer, 1); // ת�����͡��������ֶΡ�
                    String transferState = ByteStrHelper.getString(dataBuffer, 1); // ת��״̬���������ֶΡ�
                    ByteStrHelper.getString(dataBuffer, 6); // �����ֶ�
                    
                    String stockCode = market + code;
                    
                    Stock stock = ServerCache.getStock(stockCode);
                    if ( stock == null )
                    {
                        stock = new Stock();
                        stock.setStockCode(stockCode);
                        ServerCache.cacheStock(stock);
                    }
                    
                    stock.setSourceTime(HQState.get(market).getSourceTime());
                    
                    stock.setSerno(serno);
                    stock.setCode(code);
                    stock.setName(name);
                    stock.setStktype(stktype);
                    stock.setCurdate(curdate);
                    stock.setMarket(market);
                    stock.setMinutes(minutes);
                    
                    stock.setBuyprice1(buyprice1);
                    stock.setBuyprice2(buyprice2);
                    stock.setBuyprice3(buyprice3);
                    stock.setBuyprice4(buyprice4);
                    stock.setBuyprice5(buyprice5);
                    stock.setSellprice1(sellprice1);
                    stock.setSellprice2(sellprice2);
                    stock.setSellprice3(sellprice3);
                    stock.setSellprice4(sellprice4);
                    stock.setSellprice5(sellprice5);
                    stock.setBuyvol1(buyvol1);
                    stock.setBuyvol2(buyvol2);
                    stock.setBuyvol3(buyvol3);
                    stock.setBuyvol4(buyvol4);
                    stock.setBuyvol5(buyvol5);
                    stock.setSellvol1(sellvol1);
                    stock.setSellvol2(sellvol2);
                    stock.setSellvol3(sellvol3);
                    stock.setSellvol4(sellvol4);
                    stock.setSellvol5(sellvol5);
                    
                    stock.setYesterday(yesterday);
                    stock.setOpen(open);
                    stock.setHigh(high);
                    stock.setLow(low);
                    
                    /* ���Ͼ���ʱ���ڣ��ּ���0����Ϊ���� */
                    if ( now == 0 )
                    {
                        stock.setNow(yesterday);
                    }
                    else
                    {
                        stock.setNow(now);
                    }
                    
                    // stock.setNow(now);
                    stock.setVolume(volume);
                    stock.setAmount(amount);
                    stock.setOutside(outside);
                    stock.setInside(inside);
                    stock.setUp(up);
                    
                    stock.setUppercent(uppercent);
                    stock.setThedeal(thedeal);
                    stock.setVolrate(volrate);
                    stock.setMin5change(min5change);
                    
                    // -- ͣ�Ʊ�ʶ
                    int issuspend = 2;
                    if ( "T".equals(isSuspended) || "t".equals(isSuspended) )
                    {
                        issuspend = 1;// ͣ��
                    }
                    else
                    {
                        issuspend = 2;
                    }
                    stock.setSuspendMark(issuspend);// ͣ�Ʊ�ʶ
                    
                    stock.setMarginTradMark(marginTradMark);
                    stock.setTransferType(transferType);
                    stock.setTransferState(transferState);
                    
                    // -- ���ӿ� 2015��12��23�� ����4:13:50 -- �� ���ڸ������ĳ˳����㣬ˮ�� С�ģ����� ��
                    int limitUp = 0;
                    int limitDown = 0;
                    int iYesterday = Math.round(yesterday * 1000);
                    if ( name.startsWith("*ST") || name.startsWith("ST") )
                    {
                        limitUp = iYesterday * 105;
                        limitDown = iYesterday * 95;
                    }
                    else if ( name.startsWith("N") )
                    {
                        limitUp = iYesterday * 144;
                        limitDown = iYesterday * 640;
                    }
                    else
                    {
                        limitUp = iYesterday * 110;
                        limitDown = iYesterday * 90;
                    }
                    
                    stock.setLimitUp((float) (limitUp * 0.00001));
                    stock.setLimitDown((float) (limitDown * 0.00001));
                    
                }
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            MyLog.serverLog.warn("	--	@��ȡ����	--	��ȡ�����������ݳ���", e);
        }
    }
    
    public static void main(String[] args)
    {
        NetConnManager.init();
        queryMCState();
    }
    
    /**
     * @����: ��������ȡ ���и۹�ͨ����
     * @����: ���ӿ�
     * @��������: 2016��5��12�� ����10:05:20
     */
    @SuppressWarnings("unused")
    public static final void queryHKAllStock()
    {
        try
        {
            Message msg = new Message();
            msg.setFuncNo(30004);
            
            byte[] data = queryData(msg);
            
            if ( data != null )
            {
                int STRUCT_LENGTH = 239;
                
                ByteBuffer dataBuffer = ByteBuffer.wrap(data);
                dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
                
                int size = data.length / STRUCT_LENGTH;
                for (int i = 0; i < size; i++)
                {
                    String MDStreamID = ByteStrHelper.getString(dataBuffer, 6);
                    String code = ByteStrHelper.getString(dataBuffer, 6);
                    String name = toDBC(ByteStrHelper.getString(dataBuffer, 33));
                    String nameEn = ByteStrHelper.getString(dataBuffer, 16);
                    long volume = dataBuffer.getLong();
                    double amount = dataBuffer.getDouble();
                    double yesClose = dataBuffer.getDouble();
                    double nominalPrice = dataBuffer.getDouble();
                    double HighPrice = dataBuffer.getDouble();
                    double LowPrice = dataBuffer.getDouble();
                    double TradePrice = dataBuffer.getDouble();// �ּ�
                    double BuyPrice1 = dataBuffer.getDouble();
                    long BuyVolume1 = dataBuffer.getLong();
                    double SellPrice1 = dataBuffer.getDouble();
                    long SellVolume1 = dataBuffer.getLong();
                    String SecTradingStatus = ByteStrHelper.getString(dataBuffer, 9);
                    String Timestamp = ByteStrHelper.getString(dataBuffer, 13);
                    int RoundLot = dataBuffer.getInt();
                    double openPrice = dataBuffer.getDouble();
                    long Thedeal = dataBuffer.getLong();
                    ByteStrHelper.getString(dataBuffer, 1);
                    long Inside = dataBuffer.getLong();
                    long Outside = dataBuffer.getLong();
                    int Inoutflag = dataBuffer.getInt();
                    float Thechange = dataBuffer.getFloat();
                    int Dealno = dataBuffer.getInt();
                    int Serno = dataBuffer.getInt();
                    String Pyname = ByteStrHelper.getString(dataBuffer, 15);
                    
                    /* ���Ͼ���ʱ���ڣ��ּ���0����Ϊ���� */
                    if ( TradePrice == 0 )
                    {
                        if ( openPrice == 0 )
                        {
                            TradePrice = yesClose;
                        }
                        else
                        {
                            TradePrice = openPrice;
                        }
                    }
                    
                    int suspendMark = 0;
                    if ( SecTradingStatus != null && SecTradingStatus.length() >= 1 )
                    {
                        if ( SecTradingStatus.substring(0, 1).equalsIgnoreCase("1") )
                        {
                            suspendMark = 1;// ͣ��
                        }
                        else
                        {
                            suspendMark = 2;
                        }
                    }
                    
                    String market = "HK";
                    String stockCode = market + code;
                    
                    Stock stock = ServerCache.getStock(stockCode);
                    
                    if ( stock == null )
                    {
                        stock = new Stock();
                        stock.setStockCode(stockCode);
                        ServerCache.cacheStock(stock);
                    }
                    
                    stock.setSourceTime(HQState.get(market).getSourceTime());
                    
                    stock.setCode(code);
                    stock.setName(name);
                    stock.setVolume(volume);
                    stock.setAmount(amount);
                    stock.setYesterday((float) yesClose);
                    stock.setHigh((float) HighPrice);
                    stock.setLow((float) LowPrice);
                    stock.setNow((float) TradePrice);
                    stock.setBuyprice1((float) BuyPrice1);
                    stock.setBuyvol1(BuyVolume1);
                    stock.setSellprice1((float) SellPrice1);
                    stock.setSellvol1(SellVolume1);
                    stock.setOpen((float) openPrice);
                    stock.setThedeal(Thedeal);
                    stock.setInside(Inside);
                    stock.setOutside(Outside);
                    
                    stock.setSuspendMark(suspendMark);
                }
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            MyLog.serverLog.warn("	--	@��ȡ����	--	��ȡ�۹�ͨ�������ݳ���", e);
        }
    }
    
    /**
     * @����: ��������ȡ���ؼ�ת���״̬
     * @����: ���ӿ�
     * @��������: 2016��5��12�� ����10:04:24
     * @return
     */
    public static MCState queryMCState()
    {
        return queryMCState(null);
    }
    
    /**
     * @����: ��ȡ��Ʊ��������
     * @����: ���ӿ�
     * @��������: 2016��6��30�� ����5:01:08
     * @throws Exception
     */
    public static void queryAllBaseInfo()
    {
        try
        {
            Message msg = new Message();
            msg.setFuncNo(10006);
            
            byte[] data = queryData(msg);
            
            if ( data != null )
            {
                Map<String, String> itemMap = new HashMap<>();
                
                String content = new String(data, Library.getEncoding());
                String[] dataArr = content.split(STX);
                String[] keyArray = null;
                String valueArray[] = null;
                Stock stock = null;
                String ltagStr = null;
                float ltag = 0;
                
                for (int i = 0; i < dataArr.length; i++)
                {
                    if ( i == 0 )
                    {
                        keyArray = dataArr[i].split(SOH);
                    }
                    else
                    {
                        valueArray = dataArr[i].split(SOH);
                        
                        if ( valueArray == null || valueArray.length < 2 )
                        {
                            continue;
                        }
                        
                        if ( "0".equals(valueArray[0]) )
                        {
                            // 0��ʾ�������г�
                            valueArray[0] = "SZ";
                        }
                        else
                        {
                            valueArray[0] = "SH";
                        }
                        
                        for (int j = 0; j < keyArray.length; j++)
                        {
                            itemMap.put(keyArray[j], valueArray[j]);
                        }
                        
                        stock = ServerCache.getStock(valueArray[0] + valueArray[1]);
                        
                        if ( stock == null )
                        {
                            continue;
                        }
                        
                        ltagStr = itemMap.get("LTAG");
                        if ( ltagStr != null )
                        {
                            ltag = MyStringUtil.strToFloat(ltagStr);
                        }
                        
                        stock.setLtag(ltag);
                    }
                }
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            MyLog.serverLog.warn("	--	@��ȡ����	--	��ȡ����������ݳ���", e);
        }
    }
    
    /**
     * @����: ��ָ����ַ��������ȡ���ؼ�ת���״̬
     * @����: ���ӿ�
     * @��������: 2016��5��12�� ����10:04:24
     * @return
     */
    public static MCState queryMCState(InetSocketAddress address)
    {
        try
        {
            Message msg = new Message();
            msg.setFuncNo(90001);
            
            byte[] data = queryData(msg, address);
            
            MCState state = null;
            if ( data != null && data.length == 34 )
            {
                ByteBuffer dataBuffer = ByteBuffer.wrap(data);
                dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
                int currentDate = dataBuffer.getInt();
                int initDate = dataBuffer.getInt();
                boolean needUpdate = dataBuffer.getShort() > 0 ? true : false;
                long dbfTime = dataBuffer.getLong();
                long hkdbfTime = dataBuffer.getLong();
                int hkcurrentDate = dataBuffer.getInt();
                int hkInitDate = dataBuffer.getInt();
                
                state = new MCState();
                
                if ( currentDate != 0 )
                {
                    state.setCurHqDate(String.valueOf(currentDate));
                }
                if ( initDate != 0 )
                {
                    state.setInitDate(String.valueOf(initDate));
                }
                state.setNeedUpdate(needUpdate);
                state.setDbfTime(dbfTime);
                
                if ( hkdbfTime != 0 )
                {
                    state.setHkdbfTime(hkdbfTime);
                }
                
                // �۹ɳ�ʼ��ʱ�� ���ӿ� 2014-12-31
                if ( hkcurrentDate != 0 )
                {
                    state.setHkCurHqDate(String.valueOf(hkcurrentDate));
                }
                if ( hkInitDate != 0 )
                {
                    state.setHkInitDate(String.valueOf(hkInitDate));
                }
            }
            return state;
            
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            MyLog.serverLog.warn("	--	@��ȡ����	--	��ȡת���״̬���ݳ���", e);
        }
        
        return null;
    }
    
    /**
     * @����: ��ȫ���ַ�ת���ɰ���ַ�
     * @����: ���ӿ�
     * @��������: 2015��9��16�� ����3:56:30
     */
    public static String toDBC(String input)
    {
        if ( null != input )
        {
            char c[] = input.toCharArray();
            for (int i = 0; i < c.length; i++)
            {
                if ( '\u3000' == c[i] )
                {
                    c[i] = ' ';
                }
                else if ( c[i] > '\uFF00' && c[i] < '\uFF5F' )
                {
                    c[i] = (char) (c[i] - 65248);
                }
            }
            String dbc = new String(c);
            return dbc.trim();
        }
        else
        {
            return null;
        }
    }
}
