package org.throwable.protocol.support;

import io.netty.channel.ChannelHandler;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/16 11:56
 */
public interface ChannelInitializerExtend {

	ChannelInitializerExtend addChannelHandler(ChannelHandler channelHandler);
}
