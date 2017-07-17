package org.throwable.protocol.serialize.hessian;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 17:58
 */
public class HessianSerializerFactory extends BasePooledObjectFactory<HessianSerializer>{

	@Override
	public HessianSerializer create() throws Exception {
		return new HessianSerializer();
	}

	@Override
	public PooledObject<HessianSerializer> wrap(HessianSerializer hessianSerializer) {
		return new DefaultPooledObject<>(hessianSerializer);
	}
}
