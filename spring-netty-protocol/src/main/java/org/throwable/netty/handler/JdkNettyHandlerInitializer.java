package org.throwable.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.throwable.protocol.codec.Encipheror;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:52
 */
public class JdkNettyHandlerInitializer implements NettyHandlerInitializer {

	private ChannelPipeline delegate;

	@Override
	public <T> void createChannelPipelineChain(ChannelPipeline pipeline, Class<T> clazz) {
		delegate = pipeline;
		delegate.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,
				Encipheror.MESSAGE_LENGTH, 0, Encipheror.MESSAGE_LENGTH));
		delegate.addLast(new LengthFieldPrepender(Encipheror.MESSAGE_LENGTH));
		delegate.addLast(new ObjectEncoder());
		delegate.addLast(new ObjectDecoder(Integer.MAX_VALUE,
				ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
	}

	@Override
	public JdkNettyHandlerInitializer addChannelHandler(ChannelHandler channelHandler) {
		delegate.addLast(channelHandler);
		return this;
	}

	@Override
	public ChannelPipeline getChannelPipeline() {
		return delegate;
	}
}
