package org.throwable.protocol.codec;

import com.google.common.io.Closer;
import io.netty.buffer.ByteBuf;
import org.throwable.protocol.codec.Encipheror;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 18:09
 */
public abstract class AbstractEncipheror implements Encipheror {

	private static Closer closer = Closer.create();

	@Override
	public void encode(final ByteBuf out,final Object message) throws IOException {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			closer.register(byteArrayOutputStream);
			encode(byteArrayOutputStream,message);
			byte[] body = byteArrayOutputStream.toByteArray();
			int dataLength = body.length;
			out.writeInt(dataLength);
			out.writeBytes(body);
		} finally {
			closer.close();
		}
	}

	@Override
	public <T> T decode(byte[] body, Class<T> clazz) throws IOException {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
			closer.register(byteArrayInputStream);
			return decode(byteArrayInputStream, clazz);
		} finally {
			closer.close();
		}
	}

	protected abstract void encode(ByteArrayOutputStream outputStream,Object message) throws IOException;

	protected abstract <T> T decode(ByteArrayInputStream inputStream, Class<T> clazz) throws IOException;
}
