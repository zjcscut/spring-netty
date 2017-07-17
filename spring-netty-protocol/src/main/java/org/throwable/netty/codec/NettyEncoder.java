package org.throwable.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.throwable.protocol.codec.Encipheror;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 11:47
 */
public class NettyEncoder extends MessageToByteEncoder<Object> {

	private Encipheror encipheror;

	public NettyEncoder(Encipheror encipheror) {
		this.encipheror = encipheror;
	}

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext,
						  Object o, ByteBuf out) throws Exception {
		this.encipheror.encode(out, o);
	}
}
