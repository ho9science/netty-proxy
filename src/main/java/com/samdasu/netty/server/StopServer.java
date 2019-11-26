package com.samdasu.netty.server;


import com.samdasu.netty.client.SamdasuClientInitializer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class StopServer {

	private static String HOST;
    private static int PORT;

    public StopServer() {
    	HOST = "127.0.0.1";
    	PORT = 9911;
    }
    
    public void run() throws InterruptedException {
    	EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new SamdasuClientInitializer());
            
            ChannelFuture f = b.connect(HOST, PORT).sync();
            
            String query = "makingtheworldrunbetterbydata";
            f.channel().writeAndFlush(query);
            f.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }
}
