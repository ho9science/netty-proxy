package com.samdasu.netty.server;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * netty 서버 설정 
 * 
 * @author samdasu
 *
 */
public class SamdasuServer {
	private static int PORT;
	private static String HOST;
	
	public EventLoopGroup bossGroup;
	public EventLoopGroup workerGroup;

	public SamdasuServer() {
		HOST = "127.0.0.1";
		PORT = 9911;
	}
	
    public void run() throws Exception {
        // accept된 Channel을 WorkerGroup에 전달하는 역할
    	bossGroup = new NioEventLoopGroup(1);
        // 이벤트 및 작업 처리를 위한 실직적인 EventLoop를 Channel에 할당하는 역할, 스레드의 개수는 process*2
    	workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()*2);
        try {
            // port를 bind하기 때문에 serverbootstrap생성
        	ServerBootstrap serverBootstrap = new ServerBootstrap();
            // EventLoopGroup 할당
        	serverBootstrap.group(bossGroup, workerGroup)
                    // NioEventLoopGroup을 사용하기 때문에 NioServerSocketChannel 사용
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 연결이 수락될 때마다 호출될 ChannelInitializer를 지정
                    .childHandler(new SamdasuServerInitializer());
            // port에 bind 시도를 하고 bind 될때까지 block
        	ChannelFuture f = serverBootstrap.bind(HOST, PORT).sync();
        	// 채널이 close 될때까지 block
            f.channel().closeFuture().sync();
        } finally {
            //대기중인 이벤트와 작업을 모두 처리 or 종료
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    public void stopServer(){
    	if( workerGroup != null) {
    		workerGroup.shutdownGracefully(0, 5, TimeUnit.SECONDS);
    	}
    	if( bossGroup != null) {
    		bossGroup.shutdownGracefully(0, 5, TimeUnit.SECONDS).syncUninterruptibly();
    	}
		System.exit(0);
	}
}