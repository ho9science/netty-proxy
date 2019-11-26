package com.samdasu.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

/**
 * netty 클라이언트
 * 
 * @author samdasu
 *
 */
public class SamdasuClient {
    static String HOST;
    static int PORT;
    private ChannelFuture channelfuture;
    private EventLoopGroup group;
    private Bootstrap b;
	
    public SamdasuClient(String host, int port) {
    	HOST = host;
    	PORT = port;
    }
    
    private void run() throws InterruptedException {
          group = new NioEventLoopGroup();
          try {
              b = new Bootstrap();
              b.group(group)
                      .channel(NioSocketChannel.class)
                      .option(ChannelOption.TCP_NODELAY, true)
                      .handler(new SamdasuClientInitializer());
              channelfuture = b.connect(HOST, PORT).sync();
          } finally {

          }
    }
    
    private String factorialHandler(ChannelFuture channelfuture) {
    	SamdasuClientInboundHandler handler = (SamdasuClientInboundHandler) channelfuture.channel().pipeline().last();
    	return handler.getMessage();
    }
    
    public String query(String query) {
    	try {
            this.run();
            channelfuture.channel().writeAndFlush("query");
            String result = factorialHandler(channelfuture);
            close();
            return result;
        }catch(Exception e) {
    		return "{\"error\":\"연결 실패\"}";
    	}
    }
    
    private void close() throws InterruptedException {
    	b = null;
    	group.shutdownGracefully(0, 5, TimeUnit.SECONDS);
    }
    
}