package org.throwable.protocol.support;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.throwable.netty.handler.NettyHandlerInitializer;
import org.throwable.protocol.serialize.DefaultSerializationProtocolSelector;
import org.throwable.protocol.serialize.SerializationProtocolEnum;
import org.throwable.protocol.serialize.SerializationProtocolSelector;

import java.util.LinkedList;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/16 11:54
 */
public class ChannelInitializerAdapter extends ChannelInitializer<SocketChannel> implements ChannelInitializerExtend {

	private final SerializationProtocolSelector protocolSelector = new DefaultSerializationProtocolSelector();
	private SerializationProtocolEnum serializationProtocolEnum;
	private Class<?> clazz;
	private final List<ChannelHandler> customChannelHandlers = new LinkedList<>();

	public ChannelInitializerAdapter initChannelInitializerAdapter(String protocol, Class<?> clazz) {
		return initChannelInitializerAdapter(SerializationProtocolEnum.forEnum(protocol), clazz);
	}

	public ChannelInitializerAdapter initChannelInitializerAdapter(SerializationProtocolEnum serializationProtocolEnum,
																   Class<?> clazz) {
		this.serializationProtocolEnum = serializationProtocolEnum;
		this.clazz = clazz;
		return this;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		NettyHandlerInitializer nettyHandlerInitializer = protocolSelector.select(serializationProtocolEnum, pipeline, clazz);
		for (ChannelHandler channelHandler : customChannelHandlers) {
			nettyHandlerInitializer.addChannelHandler(channelHandler);
		}
	}

	@Override
	public ChannelInitializerExtend addChannelHandler(ChannelHandler channelHandler) {
		customChannelHandlers.add(channelHandler);
		return this;
	}
}
