package com.samdasu.netty.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * netty message encoder for client
 * 메시지 크기만큼 ByteBuf에 헤더처럼 쓰고 메시지 붙이는 기능
 * 
 * 
 * @author samdasu
 *
 */
public class ClientMessageEncoder extends MessageToByteEncoder<byte[]>{

	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
		out.writeInt(msg.length);
		out.writeBytes(msg);
	}
}
