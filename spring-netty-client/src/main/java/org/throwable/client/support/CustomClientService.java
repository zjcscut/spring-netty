package org.throwable.client.support;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.throwable.netty.CustomRequest;
import org.throwable.netty.CustomResponse;

import java.util.UUID;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 23:04
 */
@Service
@Slf4j
public class CustomClientService {

	public void sendRequest(final ChannelHandlerContext ctx) {
		ctx.writeAndFlush(new CustomRequest(UUID.randomUUID().toString()));
	}

	public void handleResponse(CustomResponse response){
		if (log.isDebugEnabled()) {
			log.debug("Receive CustomResponse from server,response >>>> messageId:" +
					response.getMessageId() + ",statusCode:" + response.getStatusCode() + ",result:" + response.getResult());
		}
	}
}
