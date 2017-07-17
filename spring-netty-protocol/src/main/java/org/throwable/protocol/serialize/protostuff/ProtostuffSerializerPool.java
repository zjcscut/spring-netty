package org.throwable.protocol.serialize.protostuff;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.throwable.protocol.serialize.SerializerPool;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 20:27
 */
public class ProtostuffSerializerPool implements SerializerPool<ProtostuffSerializer> {
	private GenericObjectPool<ProtostuffSerializer> protostuffPool;
	private volatile static ProtostuffSerializerPool poolInstance;

	private ProtostuffSerializerPool() {
		protostuffPool = new GenericObjectPool<>(new ProtostuffSerializerFactory());
	}

	public static ProtostuffSerializerPool getProtostuffPoolInstance() {
		if (null == poolInstance) {
			synchronized (ProtostuffSerializerPool.class) {
				if (null == poolInstance) {
					poolInstance = new ProtostuffSerializerPool();
				}
			}
		}
		return poolInstance;
	}

	public ProtostuffSerializerPool(final int maxTotal, final int minIdle, final long maxWaitMillis, final long minEvictableIdleTimeMillis) {
		protostuffPool = new GenericObjectPool<>(new ProtostuffSerializerFactory());
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		protostuffPool.setConfig(config);
	}

	@Override
	public ProtostuffSerializer borrowResource() {
		try {
			return getPool().borrowObject();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void returnResource(ProtostuffSerializer protostuffSerializer) {
		getPool().returnObject(protostuffSerializer);
	}

	@Override
	public GenericObjectPool<ProtostuffSerializer> getPool() {
		return protostuffPool;
	}
}
