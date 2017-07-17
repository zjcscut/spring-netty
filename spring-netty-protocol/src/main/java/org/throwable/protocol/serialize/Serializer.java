package org.throwable.protocol.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 11:45
 */
public interface Serializer {

	void serialize(OutputStream output, Object object) throws IOException;

	<T> T deserialize(InputStream input, Class<T> clazz) throws IOException;
}
