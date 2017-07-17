package org.throwable.protocol.serialize.hessian;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.throwable.protocol.serialize.SerializerPool;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 13:22
 */
public class HessianSerializerPool implements SerializerPool<HessianSerializer> {

	private GenericObjectPool<HessianSerializer> hessianPool;
	private static volatile HessianSerializerPool poolInstance;

	private HessianSerializerPool() {
		hessianPool = new GenericObjectPool<>(new HessianSerializerFactory());
	}

	public HessianSerializerPool(final int maxTotal, final int minIdle, final long maxWaitMillis, final long minEvictableIdleTimeMillis) {
		hessianPool = new GenericObjectPool<>(new HessianSerializerFactory());
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		hessianPool.setConfig(config);
	}

	public static HessianSerializerPool getHessianPoolInstance() {
		if (null == poolInstance) {
			synchronized (HessianSerializerPool.class) {
				if (null == poolInstance) {
					poolInstance = new HessianSerializerPool();
				}
			}
		}
		return poolInstance;
	}

	@Override
	public HessianSerializer borrowResource() {
		try {
			return getPool().borrowObject();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void returnResource(HessianSerializer hessianSerializer) {
		getPool().returnObject(hessianSerializer);
	}

	@Override
	public GenericObjectPool<HessianSerializer> getPool() {
		return hessianPool;
	}
}
