package org.throwable.protocol.codec;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 11:58
 */
public interface Encipheror {

	int MESSAGE_LENGTH = 4;  //默认四bit消息头

	void encode(final ByteBuf out, final Object message) throws IOException;

	<T> T decode(byte[] body, Class<T> clazz) throws IOException;
}
