package org.throwable.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.throwable.netty.CustomRequest;
import org.throwable.netty.CustomResponse;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 22:23
 */
@Component
public class CustomNettyReceiver extends SimpleChannelInboundHandler <CustomRequest>{

	@Autowired
	private CustomServerService customServerService;

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		super.handlerAdded(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CustomRequest msg) throws Exception {
		CustomRequest request = (CustomRequest) msg;
		System.out.println("CustomRequest -----> messageId:" + request.getMessageId());
		CustomResponse response = new CustomResponse();

		customServerService.submitTask(new CustomServerTask(request, response), ctx, request, response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
