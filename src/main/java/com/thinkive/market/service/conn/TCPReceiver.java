package com.thinkive.market.service.conn;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinkive.hqwarn.cache.NetConnManager;

public class TCPReceiver extends Receiver
{
    private static Log logger          = LogFactory.getLog("Server");
                                       
    private long       lastRecieveTime = 0;
                                       
    public TCPReceiver()
    {
        setName("TCPReceiver");
        DispathWorker worker = new DispathWorker();
        worker.start();
    }
    
    public void run()
    {
        while (true)
        {
            SocketChannel channel = null;
            Selector selector = null;
            
            SocketAddress target = NetConnManager.ZMJ.getConnAddress();
            
            try
            {
                if ( target == null )
                {
                    Thread.sleep(1000);
                    continue;
                }
                
                // 获得一个Socket通道
                channel = SocketChannel.open();
                channel.configureBlocking(false);
                channel.connect(target);
                
                selector = Selector.open();
                // 注册
                channel.register(selector, SelectionKey.OP_CONNECT);
                // 轮询访问selector
                while (true)
                {
                    selector.select(30000);
                    // 获得selector中选中的项的迭代器
                    Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
                    while (ite.hasNext())
                    {
                        SelectionKey key = ite.next();
                        // 删除已选的key,以防重复处理
                        ite.remove();
                        // 连接事件发生
                        if ( key.isConnectable() )
                        {
                            SocketChannel sc = (SocketChannel) key.channel();
                            // 如果正在连接，则完成连接
                            if ( sc.isConnectionPending() )
                            {
                                sc.finishConnect();
                            }
                            // 设置成非阻塞
                            sc.configureBlocking(false);
                            int version = 0;
                            int msgType = 2;// 2表示订阅推送信息
                            ByteBuffer byteBuffer = ByteBuffer.allocate(18);
                            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                            byteBuffer.put((byte) 'T').put((byte) 'K');
                            byteBuffer.putInt(msgType);
                            byteBuffer.putInt(version);
                            byteBuffer.putInt(0);// 备用
                            byteBuffer.putInt(0);// 内容长度
                            byteBuffer.flip();
                            
                            sc.write(byteBuffer);
                            // 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                            sc.register(selector, SelectionKey.OP_READ);
                            
                        }
                        else if ( key.isReadable() )
                        {
                            // 获得了可读的事件
                            
                            SocketChannel sc = (SocketChannel) key.channel();
                            
                            ByteBuffer headBuffer = ByteBuffer.allocate(4);
                            headBuffer.order(ByteOrder.LITTLE_ENDIAN);
                            readFixedLenToBuffer(sc, headBuffer);
                            headBuffer.flip();
                            
                            int length = headBuffer.getInt();
                            
                            if ( length > 16 )
                            {
                                ByteBuffer dataBuffer = ByteBuffer.allocate(length);
                                
                                dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
                                dataBuffer.putInt(length);
                                
                                readFixedLenToBuffer(sc, dataBuffer);
                                dataBuffer.flip();
                                
                                if ( isReceived() && length > 16 )
                                {
                                    queue.offer(dataBuffer.array());
                                    count++;
                                }
                            }
                        }
                        
                        lastRecieveTime = System.currentTimeMillis();
                    }
                    
                    if ( lastRecieveTime - System.currentTimeMillis() > 60000 )
                    {
                        throw new Exception("	--	@行情通信	--	超过一分钟没有通信，服务器主动断开转码机");
                    }
                    
                }
            }
            catch (IOException e)
            {
                
                logger.warn("	--	@转码机通信	--	【转码机】连接异常 : " + target, e);
                
            }
            catch (Exception ie)
            {
                logger.warn("	--	@转码机通信	--	连接任务异常 : " + target, ie);
            }
            finally
            {
                if ( channel != null )
                {
                    try
                    {
                        channel.close();
                        channel = null;
                        
                    }
                    catch (IOException e)
                    {
                        channel = null;
                    }
                }
                
                if ( selector != null )
                {
                    try
                    {
                        selector.close();
                        selector = null;
                        
                    }
                    catch (IOException e)
                    {
                        selector = null;
                    }
                }
                
                // -- 王嵊俊 2016年7月11日 下午3:53:55 -- 【连接异常断开 启动转码机重连模块】
                
                target = NetConnManager.reconnect(NetConnManager.ZMJ);
                
                logger.warn("	--	@转码机	--	当前连接地址：" + target);
                
            }
        }
    }
    
    private void readFixedLenToBuffer(SocketChannel sc, ByteBuffer buffer) throws Exception
    {
        int count = 0;
        int bufLength = buffer.remaining();
        int readLength = 0;
        do
        {
            count = sc.read(buffer);
            if ( count == -1 ) // 已经到达末尾
            {
                throw new Exception("read data wrong");
            }
            
            readLength += count;
            
            if ( readLength == bufLength )
            {
                return;
            }
        }
        while (true);
    }
    
}
