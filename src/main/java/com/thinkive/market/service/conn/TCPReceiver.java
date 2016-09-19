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
                
                // ���һ��Socketͨ��
                channel = SocketChannel.open();
                channel.configureBlocking(false);
                channel.connect(target);
                
                selector = Selector.open();
                // ע��
                channel.register(selector, SelectionKey.OP_CONNECT);
                // ��ѯ����selector
                while (true)
                {
                    selector.select(30000);
                    // ���selector��ѡ�е���ĵ�����
                    Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
                    while (ite.hasNext())
                    {
                        SelectionKey key = ite.next();
                        // ɾ����ѡ��key,�Է��ظ�����
                        ite.remove();
                        // �����¼�����
                        if ( key.isConnectable() )
                        {
                            SocketChannel sc = (SocketChannel) key.channel();
                            // ����������ӣ����������
                            if ( sc.isConnectionPending() )
                            {
                                sc.finishConnect();
                            }
                            // ���óɷ�����
                            sc.configureBlocking(false);
                            int version = 0;
                            int msgType = 2;// 2��ʾ����������Ϣ
                            ByteBuffer byteBuffer = ByteBuffer.allocate(18);
                            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                            byteBuffer.put((byte) 'T').put((byte) 'K');
                            byteBuffer.putInt(msgType);
                            byteBuffer.putInt(version);
                            byteBuffer.putInt(0);// ����
                            byteBuffer.putInt(0);// ���ݳ���
                            byteBuffer.flip();
                            
                            sc.write(byteBuffer);
                            // �ںͷ�������ӳɹ�֮��Ϊ�˿��Խ��յ�����˵���Ϣ����Ҫ��ͨ�����ö���Ȩ�ޡ�
                            sc.register(selector, SelectionKey.OP_READ);
                            
                        }
                        else if ( key.isReadable() )
                        {
                            // ����˿ɶ����¼�
                            
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
                        throw new Exception("	--	@����ͨ��	--	����һ����û��ͨ�ţ������������Ͽ�ת���");
                    }
                    
                }
            }
            catch (IOException e)
            {
                
                logger.warn("	--	@ת���ͨ��	--	��ת����������쳣 : " + target, e);
                
            }
            catch (Exception ie)
            {
                logger.warn("	--	@ת���ͨ��	--	���������쳣 : " + target, ie);
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
                
                // -- ���ӿ� 2016��7��11�� ����3:53:55 -- �������쳣�Ͽ� ����ת�������ģ�顿
                
                target = NetConnManager.reconnect(NetConnManager.ZMJ);
                
                logger.warn("	--	@ת���	--	��ǰ���ӵ�ַ��" + target);
                
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
            if ( count == -1 ) // �Ѿ�����ĩβ
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
