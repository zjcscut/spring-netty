package org.throwable.protocol.serialize.protostuff;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.throwable.protocol.serialize.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 13:21
 */
public class ProtostuffSerializer implements Serializer {

	private static SchemaCache cachedSchema = SchemaCache.getInstance();
	private static Objenesis objenesis = new ObjenesisStd(true);

	@SuppressWarnings("unchecked")
	private static Schema<Object> getSchema(Class<?> clazz) {
		return (Schema<Object>) cachedSchema.get(clazz);
	}

	@Override
	public void serialize(OutputStream output, Object object) throws IOException {
		Class<?> clazz = object.getClass();
		Schema<Object> schema = ProtostuffSerializer.getSchema(clazz);
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			ProtostuffIOUtil.writeTo(output, object, schema, buffer);
		} finally {
			buffer.clear();
		}
	}

	@Override
	public <T> T deserialize(InputStream input, Class<T> clazz) throws IOException {
		Schema<Object> schema = ProtostuffSerializer.getSchema(clazz);
		T t = objenesis.newInstance(clazz);
		ProtostuffIOUtil.mergeFrom(input, t, schema);
		return t;
	}
}
