package com.thinkive.hqwarn.netty.codec;

import java.nio.ByteOrder;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.thinkive.base.util.Library;
import com.thinkive.hqwarn.netty.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

/**
 * @描述: 数据包编解码器
 * @版权: Copyright (c) 2016 
 * @公司: Thinkive 
 * @作者: 王嵊俊
 * @创建日期: 2016年4月19日 上午10:38:10
 */
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message>
{
    
    private static final Log logger = LogFactory.getLog("Server");
                                    
    private static String    SOH    = new String("\1");
                                    
    private static String    STX    = new String("\2");
                                    
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception
    {
        // TODO Auto-generated method stub
        int funcNo = msg.getFuncNo();
        int version = msg.getVersion();
        
        StringBuffer buffer = new StringBuffer();
        
        for (String key : msg.getRequestParamKeySet())
        {
            buffer.append(key);
            buffer.append(SOH);
        }
        
        if ( buffer.length() != 0 )
        {
            buffer.substring(0, buffer.length() - 1);
        }
        
        buffer.append(STX);
        
        Object value = null;
        for (String key : msg.getRequestParamKeySet())
        {
            value = msg.getParam(key);
            buffer.append(value);
            buffer.append(SOH);
        }
        if ( buffer.length() != 0 )
        {
            buffer.substring(0, buffer.length() - 1);
        }
        
        byte[] bodyData;
        bodyData = buffer.toString().getBytes(Library.getEncoding());
        
        ByteBuf byteBuf = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
        
        byteBuf.writeByte('T').writeByte('K');
        
        byteBuf.writeInt(funcNo);
        byteBuf.writeInt(version);
        byteBuf.writeInt(0);
        byteBuf.writeInt(bodyData.length);
        byteBuf.writeBytes(bodyData);
        
        out.add(byteBuf);
    }
    
    @SuppressWarnings("unused")
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception
    {
        // TODO Auto-generated method stub
        byte[] mark = new byte[2];
        msg.readBytes(mark);
        
        if ( !isLegalPack(mark) )
        {
            return;
        }
        
        int funcNo = msg.readInt();
        int version = msg.readInt();
        int keepField = msg.readInt();
        int bodyLength = msg.readInt();
        
        byte[] data = new byte[msg.readableBytes()];
        msg.readBytes(data);
        
        if ( funcNo == 10002 )
        {
            logger.warn("  --  @功能号调用出错        -- 错误信息    ：   " + new String(data));
        }
        
        Message message = new Message();
        
        message.setFuncNo(funcNo);
        message.setVersion(version);
        message.setData(data);
        
        out.add(message);
    }
    
    private boolean isLegalPack(byte[] mark)
    {
        if ( mark.length == 2 )
        {
            return mark[0] == 'T' && mark[1] == 'K';
        }
        return false;
    }
    
}
