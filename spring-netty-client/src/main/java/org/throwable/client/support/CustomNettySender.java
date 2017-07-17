package org.throwable.client.support;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.throwable.netty.CustomResponse;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 22:23
 */
@Slf4j
@Component
public class CustomNettySender extends SimpleChannelInboundHandler<CustomResponse> {

	@Autowired
	private CustomClientService customClientService;

	private ChannelHandlerContext context;

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		this.context = ctx;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CustomResponse response) throws Exception {
		customClientService.handleResponse(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	public void sendRequestMessage() {
		customClientService.sendRequest(context);
	}
}
