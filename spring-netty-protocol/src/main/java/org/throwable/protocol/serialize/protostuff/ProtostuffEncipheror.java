package org.throwable.protocol.serialize.protostuff;

import org.throwable.protocol.codec.AbstractEncipheror;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 12:12
 */
public class ProtostuffEncipheror extends AbstractEncipheror {

	private ProtostuffSerializerPool protostuffSerializerPool = ProtostuffSerializerPool.getProtostuffPoolInstance();

	@Override
	protected void encode(ByteArrayOutputStream outputStream, Object message) throws IOException {
		ProtostuffSerializer protostuffSerializer = protostuffSerializerPool.borrowResource();
		try {
			protostuffSerializer.serialize(outputStream, message);
		} finally {
			protostuffSerializerPool.returnResource(protostuffSerializer);
		}
	}

	@Override
	protected <T> T decode(ByteArrayInputStream inputStream, Class<T> clazz) throws IOException {
		ProtostuffSerializer protostuffSerializer = protostuffSerializerPool.borrowResource();
		try {
			return protostuffSerializer.deserialize(inputStream, clazz);
		} finally {
			protostuffSerializerPool.returnResource(protostuffSerializer);
		}
	}
}
