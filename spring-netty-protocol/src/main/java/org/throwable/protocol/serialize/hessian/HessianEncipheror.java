package org.throwable.protocol.serialize.hessian;

import org.throwable.protocol.codec.AbstractEncipheror;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 12:07
 */
public class HessianEncipheror extends AbstractEncipheror {

	private HessianSerializerPool hessianSerializerPool = HessianSerializerPool.getHessianPoolInstance();

	@Override
	protected void encode(ByteArrayOutputStream outputStream, Object message) throws IOException {
		HessianSerializer hessianSerializer = hessianSerializerPool.borrowResource();
		try {
			hessianSerializer.serialize(outputStream, message);
		} finally {
			hessianSerializerPool.returnResource(hessianSerializer);
		}
	}

	@Override
	protected <T> T decode(ByteArrayInputStream inputStream, Class<T> clazz) throws IOException {
		HessianSerializer hessianSerializer = hessianSerializerPool.borrowResource();
		try {
			return hessianSerializer.deserialize(inputStream, clazz);
		} finally {
			hessianSerializerPool.returnResource(hessianSerializer);
		}
	}
}
