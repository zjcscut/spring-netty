package org.throwable.protocol.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import org.throwable.protocol.serialize.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 12:17
 */
public class KyroSerializer implements Serializer {

	private KryoPool kryoPool = KryoPoolFactory.getKryoPoolInstance();

	@Override
	public void serialize(OutputStream output, Object object) throws IOException {
		Kryo kryo = kryoPool.borrow();
		try (Output out = new Output(output)) {
			kryo.writeClassAndObject(out, object);
		} finally {
			output.close();
			kryoPool.release(kryo);
		}
	}

	@Override
	public <T> T deserialize(InputStream input, Class<T> clazz) throws IOException {
		Kryo kryo = kryoPool.borrow();
		try (Input in = new Input(input)) {
			return kryo.readObject(in, clazz);
		} finally {
			input.close();
			kryoPool.release(kryo);
		}
	}
}
