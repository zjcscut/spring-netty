package org.throwable.protocol.serialize.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.throwable.protocol.serialize.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 12:13
 */
public class HessianSerializer implements Serializer {

	@Override
	public void serialize(OutputStream output, Object object) throws IOException {
		Hessian2Output ho = new Hessian2Output(output);
		try {
			ho.startMessage();
			ho.writeObject(object);
			ho.completeMessage();
		} finally {
			ho.close();
			output.close();
		}
	}

	@Override
	public <T> T deserialize(InputStream input, Class<T> clazz) throws IOException {
		T result;
		Hessian2Input hi = new Hessian2Input(input);
		try {
			hi.startMessage();
			result = clazz.cast(hi.readObject(clazz));
			hi.completeMessage();
		} finally {
			hi.close();
			input.close();
		}
		return result;
	}
}
