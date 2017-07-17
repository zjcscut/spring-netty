package org.throwable.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:47
 */
public interface NettyHandlerInitializer {

	<T> void createChannelPipelineChain(ChannelPipeline pipeline, Class<T> clazz);

	NettyHandlerInitializer addChannelHandler(ChannelHandler channelHandler);

	ChannelPipeline getChannelPipeline();
}
