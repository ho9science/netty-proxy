package com.samdasu.netty.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.util.JsonFormat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
/**
 * netty handler 
 * 
 * @author samdasu
 *
 */
public class SamdasuServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    	if("makingtheworldrunbetterbydata".equals(msg)) {
    		ctx.channel().close();
    		ctx.channel().parent().close();
    		ctx.channel().closeFuture().sync();
    		new SamdasuServer().stopServer();
    	}else {
    		ctx.writeAndFlush("result");
    		ctx.close();    		
    	}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}