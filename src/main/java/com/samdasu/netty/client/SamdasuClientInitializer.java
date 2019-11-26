package com.samdasu.netty.client;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;


public class SamdasuClientInitializer extends ChannelInitializer<SocketChannel>{
	
	public SamdasuClientInitializer() {
	
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
//		.addLast(new ClientReplayingDecoder(), new ClientMessageEncoder())
		.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
		.addLast(new ProtobufDecoder(AnswerProtos.Answer.getDefaultInstance()))
		.addLast(new LengthFieldPrepender(4))
		.addLast(new ProtobufEncoder())
		.addLast(new SamdasuClientInboundHandler());
	}
}

