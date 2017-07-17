package org.throwable.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.throwable.client.support.CustomChannelInitializer;
import org.throwable.client.support.CustomNettySender;
import org.throwable.netty.CustomResponse;
import org.throwable.netty.codec.NettyDecoder;
import org.throwable.netty.codec.NettyEncoder;
import org.throwable.protocol.serialize.SerializationProtocolEnum;
import org.throwable.protocol.serialize.hessian.HessianEncipheror;
import org.throwable.protocol.serialize.kyro.KyroEncipheror;
import org.throwable.protocol.serialize.protostuff.ProtostuffEncipheror;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:25
 */
@Slf4j
@Component
public class NettyClient implements EnvironmentAware, ApplicationContextAware, InitializingBean, DisposableBean {

	private ApplicationContext applicationContext;
	private int parallel = Math.max(2, Runtime.getRuntime().availableProcessors()) * 2;
	private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);
	private String protocol;
	private String serverAddress;
	@Autowired
	private CustomChannelInitializer customChannelInitializer;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.protocol = environment.getProperty("protocol", "hessian");
		this.serverAddress = environment.getProperty("serverAddress");
	}

	@Override
	public void destroy() throws Exception {
		stop();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		start();
	}

	public void start() {
		try {
			customChannelInitializer.initChannelInitializer(SerializationProtocolEnum.forEnum(protocol), CustomResponse.class);
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
					.handler(customChannelInitializer);
//					.handler(new ChannelInitializer<SocketChannel>() {
//
//						@Override
//						protected void initChannel(SocketChannel ch) throws Exception {
//							ch.pipeline().addLast(new NettyEncoder(new HessianEncipheror()));
//							ch.pipeline().addLast(new NettyDecoder<>(new HessianEncipheror(),CustomResponse.class));
//							ch.pipeline().addLast(customNettySender);
//						}
//
//					});
			String[] ipAddr = serverAddress.split(":");
			if (ipAddr.length == 2) {
				String host = ipAddr[0];
				int port = Integer.parseInt(ipAddr[1]);
				ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
				channelFuture.addListener((ChannelFutureListener) future -> {
					if (future.isSuccess()) {
						if (log.isDebugEnabled()) {
							log.debug("Netty client connected successfully!");
						}
					}
				});
			} else {
				throw new IllegalArgumentException("Server address parse failed!Please check this configuration property!");
			}
		} catch (Exception e) {
			log.error("Start netty client failed!", e);
			throw new IllegalStateException(e);
		}
	}

	public void stop() {
		eventLoopGroup.shutdownGracefully();
	}
}
