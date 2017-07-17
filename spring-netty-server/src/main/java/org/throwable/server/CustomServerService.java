package org.throwable.server;

import com.google.common.util.concurrent.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.throwable.netty.CustomRequest;
import org.throwable.netty.CustomResponse;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 22:50
 */
@Slf4j
@Service
public class CustomServerService implements InitializingBean {

	private static volatile ListeningExecutorService threadPoolExecutor;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (null == threadPoolExecutor) {
			synchronized (CustomServerService.class) {
				if (null == threadPoolExecutor) {
					threadPoolExecutor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
				}
			}
		}
	}

	public void submitTask(Callable<Boolean> task, final ChannelHandlerContext ctx,
								  final CustomRequest request, final CustomResponse response) {
		ListenableFuture<Boolean> listenableFuture = threadPoolExecutor.submit(task);
		Futures.addCallback(listenableFuture, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(@Nullable Boolean aBoolean) {
				ctx.writeAndFlush(response).addListener((ChannelFutureListener) future -> {
					if (log.isDebugEnabled()) {
						log.debug("Netty Server handle task successfully,messageId:" + request.getMessageId());
					}
				});
			}

			@Override
			public void onFailure(Throwable throwable) {
				log.error("Netty Server handle task failed,messageId:" + request.getMessageId(), throwable);
			}
		}, threadPoolExecutor);
	}
}
