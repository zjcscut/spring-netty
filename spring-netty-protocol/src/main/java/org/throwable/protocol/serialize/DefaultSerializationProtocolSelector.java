package org.throwable.protocol.serialize;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import io.netty.channel.ChannelPipeline;
import org.throwable.netty.handler.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:46
 */
public class DefaultSerializationProtocolSelector implements SerializationProtocolSelector {

	private static ClassToInstanceMap<NettyHandlerInitializer> nettyHandlers = MutableClassToInstanceMap.create();

	static {
		nettyHandlers.putInstance(JdkNettyHandlerInitializer.class, new JdkNettyHandlerInitializer());
		nettyHandlers.putInstance(KyroNettyHandlerInitializer.class, new KyroNettyHandlerInitializer());
		nettyHandlers.putInstance(HessianNettyHandlerInitializer.class, new HessianNettyHandlerInitializer());
		nettyHandlers.putInstance(ProtostuffNettyHandlerInitializer.class, new ProtostuffNettyHandlerInitializer());
	}

	@Override
	public NettyHandlerInitializer select(SerializationProtocolEnum protocolEnum,
										  ChannelPipeline pipeline,
										  Class<?> clazz) {
		NettyHandlerInitializer handler = null;
		switch (protocolEnum) {
			case JDK:
				handler = nettyHandlers.getInstance(JdkNettyHandlerInitializer.class);
				handler.createChannelPipelineChain(pipeline, clazz);
				break;
			case KRYO:
				handler = nettyHandlers.getInstance(KyroNettyHandlerInitializer.class);
				handler.createChannelPipelineChain(pipeline, clazz);
				break;
			case HESSIAN:
				handler = nettyHandlers.getInstance(HessianNettyHandlerInitializer.class);
				handler.createChannelPipelineChain(pipeline, clazz);
				break;
			case PROTOSTUFF:
				handler = nettyHandlers.getInstance(ProtostuffNettyHandlerInitializer.class);
				handler.createChannelPipelineChain(pipeline, clazz);
				break;
		}
		return handler;
	}
}
