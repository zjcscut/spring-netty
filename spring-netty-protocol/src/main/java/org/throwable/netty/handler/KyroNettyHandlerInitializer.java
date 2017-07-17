package org.throwable.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import org.throwable.netty.codec.NettyDecoder;
import org.throwable.netty.codec.NettyEncoder;
import org.throwable.protocol.serialize.kyro.KyroEncipheror;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:55
 */
public class KyroNettyHandlerInitializer implements NettyHandlerInitializer {

	private ChannelPipeline delegate;

	@Override
	public <T> void createChannelPipelineChain(ChannelPipeline pipeline, Class<T> clazz) {
		delegate = pipeline;
		delegate.addLast(new NettyEncoder(new KyroEncipheror()));
		delegate.addLast(new NettyDecoder<>(new KyroEncipheror(), clazz));
	}

	@Override
	public KyroNettyHandlerInitializer addChannelHandler(ChannelHandler channelHandler) {
		delegate.addLast(channelHandler);
		return this;
	}

	@Override
	public ChannelPipeline getChannelPipeline() {
		return delegate;
	}
}
