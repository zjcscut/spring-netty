package org.throwable.protocol.serialize;

import io.netty.channel.ChannelPipeline;
import org.throwable.netty.handler.NettyHandlerInitializer;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:42
 */
public interface SerializationProtocolSelector {

	NettyHandlerInitializer select(SerializationProtocolEnum protocolEnum, ChannelPipeline pipeline, Class<?> clazz);

}
