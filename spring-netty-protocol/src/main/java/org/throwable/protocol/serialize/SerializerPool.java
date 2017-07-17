package org.throwable.protocol.serialize;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 18:00
 */
public interface SerializerPool<T> {

	T borrowResource();

	void returnResource(final T t);

	GenericObjectPool<T> getPool();
}
