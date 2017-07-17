package org.throwable.protocol.serialize.kyro;

import org.throwable.protocol.codec.AbstractEncipheror;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 12:11
 */
public class KyroEncipheror extends AbstractEncipheror {

	private final KyroSerializer kyroSerializer = new KyroSerializer();

	@Override
	protected void encode(ByteArrayOutputStream outputStream, Object message) throws IOException {
		kyroSerializer.serialize(outputStream, message);
	}

	@Override
	protected <T> T decode(ByteArrayInputStream inputStream, Class<T> clazz) throws IOException {
		return kyroSerializer.deserialize(inputStream, clazz);
	}
}
