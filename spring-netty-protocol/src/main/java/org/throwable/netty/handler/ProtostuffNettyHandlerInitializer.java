package org.throwable.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import org.throwable.netty.codec.NettyDecoder;
import org.throwable.netty.codec.NettyEncoder;
import org.throwable.protocol.serialize.protostuff.ProtostuffEncipheror;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 22:03
 */
public class ProtostuffNettyHandlerInitializer implements NettyHandlerInitializer {

	private ChannelPipeline delegate;

	@Override
	public <T> void createChannelPipelineChain(ChannelPipeline pipeline, Class<T> clazz) {
		delegate = pipeline;
		delegate.addLast(new NettyEncoder(new ProtostuffEncipheror()));
		delegate.addLast(new NettyDecoder<>(new ProtostuffEncipheror(), clazz));
	}

	@Override
	public ProtostuffNettyHandlerInitializer addChannelHandler(ChannelHandler channelHandler) {
		delegate.addLast(channelHandler);
		return this;
	}

	@Override
	public ChannelPipeline getChannelPipeline() {
		return delegate;
	}
}
