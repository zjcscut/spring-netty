package org.throwable.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import org.throwable.netty.codec.NettyDecoder;
import org.throwable.netty.codec.NettyEncoder;
import org.throwable.protocol.serialize.hessian.HessianEncipheror;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:59
 */
public class HessianNettyHandlerInitializer implements NettyHandlerInitializer {

	private ChannelPipeline delegate;

	@Override
	public <T> void createChannelPipelineChain(ChannelPipeline pipeline, Class<T> clazz) {
		delegate = pipeline;
		pipeline.addLast(new NettyEncoder(new HessianEncipheror()));
		pipeline.addLast(new NettyDecoder<>(new HessianEncipheror(), clazz));
	}

	@Override
	public HessianNettyHandlerInitializer addChannelHandler(ChannelHandler channelHandler) {
		delegate.addLast(channelHandler);
		return this;
	}

	@Override
	public ChannelPipeline getChannelPipeline() {
		return delegate;
	}
}
