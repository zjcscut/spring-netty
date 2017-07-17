package org.throwable.client.support;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.throwable.netty.handler.NettyHandlerInitializer;
import org.throwable.protocol.serialize.DefaultSerializationProtocolSelector;
import org.throwable.protocol.serialize.SerializationProtocolEnum;
import org.throwable.protocol.serialize.SerializationProtocolSelector;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:38
 */
@Component
public class CustomChannelInitializer extends ChannelInitializer<Channel>{

	@Autowired
	private CustomNettySender customNettySender;

	final private SerializationProtocolSelector protocolSelector = new DefaultSerializationProtocolSelector();
	private SerializationProtocolEnum serializationProtocolEnum;
	private Class<?> clazz;

	public CustomChannelInitializer initChannelInitializer(SerializationProtocolEnum serializationProtocolEnum,
														   Class<?> clazz) {
		this.serializationProtocolEnum = serializationProtocolEnum;
		this.clazz = clazz;
		return this;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		NettyHandlerInitializer handler = protocolSelector.select(serializationProtocolEnum, pipeline, clazz);
		handler.addChannelHandler(customNettySender);
	}

}
