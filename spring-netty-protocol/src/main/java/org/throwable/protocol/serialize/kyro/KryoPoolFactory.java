
package org.throwable.protocol.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.throwable.netty.CustomRequest;
import org.throwable.netty.CustomResponse;


public class KryoPoolFactory{

    private static volatile KryoPoolFactory poolFactory;

    private KryoFactory factory = () -> {
		Kryo kryo = new Kryo();
		kryo.register(CustomRequest.class);
		kryo.register(CustomResponse.class);
		kryo.setReferences(false);
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		return kryo;
	};

    private KryoPool pool = new KryoPool.Builder(factory).build();

    private KryoPoolFactory() {
    }

    public static KryoPool getKryoPoolInstance() {
        if (poolFactory == null) {
            synchronized (KryoPoolFactory.class) {
                if (poolFactory == null) {
                    poolFactory = new KryoPoolFactory();
                }
            }
        }
        return poolFactory.getPool();
    }

    public KryoPool getPool() {
        return pool;
    }
}

