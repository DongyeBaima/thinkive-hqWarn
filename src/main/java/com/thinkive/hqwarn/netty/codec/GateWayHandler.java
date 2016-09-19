package com.thinkive.hqwarn.netty.codec;

import java.io.IOException;
import java.net.ConnectException;
import com.thinkive.base.util.MyLog;
import com.thinkive.hqwarn.cache.NetConnManager;
import com.thinkive.hqwarn.netty.message.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class GateWayHandler extends ChannelHandlerAdapter
{
    
    private Message message;
    
    public GateWayHandler(Message message)
    {
        // TODO Auto-generated constructor stub
        
        this.message = message;
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        // TODO Auto-generated method stub
        ctx.writeAndFlush(message);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        message.setData(((Message) msg).getData());
        
        ctx.close();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        
        if ( cause instanceof IOException || cause instanceof ConnectException )
        {
            MyLog.serverLog.error("	--	@行情通信	--	【网关】连接异常:" + NetConnManager.GATEWAY.getConnAddress(), cause);
            
            NetConnManager.reconnect(NetConnManager.GATEWAY);
            
            MyLog.serverLog.warn("	--	@行情通信	--	【网关】当前连接地址:" + NetConnManager.GATEWAY.getConnAddress(), cause);
        }
        else
        {
            MyLog.serverLog.warn("	--	@行情通信	--	", cause);
        }
        
    }
    
}
