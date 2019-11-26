package com.samdasu.netty.server.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

/**
 * netty byte decoder for server
 * 
 * @see <a href="https://netty.io/4.1/api/io/netty/handler/codec/ReplayingDecoder.html"></a>
 * 
 * @author samdasu
 *
 */
public class ServerReplayingDecoder extends ReplayingDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int length = in.readInt();
		if(in.readableBytes() < length) {
			in.resetReaderIndex();
			return;
		}
		out.add(in.readBytes(length));
	}

}
