package com.samdasu.netty.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.google.protobuf.util.JsonFormat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;


public class SamdasuClientInboundHandler extends ChannelInboundHandlerAdapter {
	final BlockingQueue<String> messageList = new LinkedBlockingQueue<String>();
	
	public SamdasuClientInboundHandler() {
		super();
	}
   
	public String getMessage() {
		boolean interrupted = false;
		try {
			for (;;) {
				try {
					return messageList.poll(15, TimeUnit.SECONDS);
				} catch(InterruptedException e) {
					interrupted = true;
					e.printStackTrace();
				}
			}
		} finally {
			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}
	}
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//    	super.channelActive(ctx);
     
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	Answer answer = (Answer) msg;
    	String result = JsonFormat.printer().print(answer);
    	boolean offered = messageList.offer(result);
    	assert offered;
    	ctx.close();
    }
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    	 if (evt == SslHandshakeCompletionEvent.SUCCESS) {
             // Your handling logging
         } else {
             super.userEventTriggered(ctx, evt);
         }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//    	System.out.println("read complete");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
