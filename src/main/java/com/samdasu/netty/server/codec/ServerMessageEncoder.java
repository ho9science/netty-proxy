package com.samdasu.netty.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * netty message encoder for server
 * 
 * 
 * @author samdasu
 *
 */
public class ServerMessageEncoder extends MessageToByteEncoder<byte[]>{

	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
		out.writeInt(msg.length);
		out.writeBytes(msg);
	}
}
