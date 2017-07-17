package org.throwable.server;

import org.throwable.netty.CustomRequest;
import org.throwable.netty.CustomResponse;

import java.util.concurrent.Callable;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 22:59
 */
public class CustomServerTask implements Callable<Boolean> {

	private CustomRequest request;
	private CustomResponse response;

	public CustomServerTask(CustomRequest request, CustomResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public Boolean call() throws Exception {
		response.setMessageId(request.getMessageId());
		Thread.sleep(5000); //模拟处理耗时
		response.setStatusCode(200);
		response.setResult("success");
		return Boolean.TRUE;
	}
}
