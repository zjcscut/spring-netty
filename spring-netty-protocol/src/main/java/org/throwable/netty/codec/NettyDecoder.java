package org.throwable.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.throwable.protocol.codec.Encipheror;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 11:46
 */
public class NettyDecoder<T> extends ByteToMessageDecoder {

	private Encipheror encipheror;
	private Class<T> clazz;

	public NettyDecoder(Encipheror encipheror, Class<T> clazz) {
		this.encipheror = encipheror;
		this.clazz = clazz;
	}

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext,
						  ByteBuf in, List<Object> list) throws Exception {
		if (in.readableBytes() < Encipheror.MESSAGE_LENGTH) {
			return;
		}
		in.markReaderIndex();
		int messageLength = in.readInt();
		if (messageLength < 0) {
			in.resetReaderIndex();
		} else {
			byte[] messageBody = new byte[messageLength];
			in.readBytes(messageBody);
			list.add(encipheror.decode(messageBody, clazz));
		}
	}
}
