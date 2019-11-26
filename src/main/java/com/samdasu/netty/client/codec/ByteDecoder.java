package com.samdasu.netty.client.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * netty byte decoder - 패킷 크기에 따라 readblebytes 조절 요망
 * 
 * 
 * @author samdasu
 *
 */
public class ByteDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes() < 2000) {
//			System.out.println(in.readableBytes());
			return;
		}
//		System.out.println(in.readableBytes());
		out.add(in.readBytes(in.readableBytes()));
		
	}
	
}
