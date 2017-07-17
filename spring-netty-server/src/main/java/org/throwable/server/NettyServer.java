package org.throwable.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
import org.throwable.netty.CustomRequest;
import org.throwable.netty.codec.NettyDecoder;
import org.throwable.netty.codec.NettyEncoder;
import org.throwable.protocol.serialize.SerializationProtocolEnum;
import org.throwable.protocol.serialize.hessian.HessianEncipheror;
import org.throwable.protocol.serialize.kyro.KyroEncipheror;
import org.throwable.protocol.serialize.protostuff.ProtostuffEncipheror;
import org.throwable.protocol.support.NamedThreadFactory;

import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadFactory;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:25
 */
@Component
@Slf4j
public class NettyServer implements EnvironmentAware, ApplicationContextAware, InitializingBean, DisposableBean {

	private ApplicationContext applicationContext;
	private int parallel = Math.max(2, Runtime.getRuntime().availableProcessors()) * 2;
	private ThreadFactory threadFactory = new NamedThreadFactory();
	private EventLoopGroup boss = new NioEventLoopGroup();
	private EventLoopGroup worker = new NioEventLoopGroup(parallel, threadFactory, SelectorProvider.provider());
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
			customChannelInitializer.initChannelInitializer(SerializationProtocolEnum.forEnum(protocol), CustomRequest.class);
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(boss, worker)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(customChannelInitializer);
//					.childHandler(new ChannelInitializer<SocketChannel>() {
//						@Override
//						protected void initChannel(SocketChannel ch) throws Exception {
//							ch.pipeline().addLast(new NettyEncoder(new HessianEncipheror()));
//							ch.pipeline().addLast(new NettyDecoder<>(new HessianEncipheror(), CustomRequest.class));
//							ch.pipeline().addLast(customNettyReceiver);
//						}
//					});
			String[] ipAddr = this.serverAddress.split(":");
			if (ipAddr.length == 2) {
				String host = ipAddr[0];
				int port = Integer.parseInt(ipAddr[1]);
				ChannelFuture channelFuture = bootstrap.bind(host, port).sync();
				channelFuture.addListener((ChannelFutureListener) future -> {
					if (future.isSuccess()) {
						if (log.isInfoEnabled()) {
							log.info(String.format("Netty Server start success!\nip:%s\nport:%d\nprotocol:%s\n\n", host, port, protocol));
						}
					}
				});
			} else {
				throw new IllegalArgumentException("Server address parse failed!Please check this configuration property!");
			}
		} catch (Exception e) {
			log.error("Start netty server failed!", e);
			throw new IllegalStateException(e);
		}
	}

	public void stop() {
		worker.shutdownGracefully();
		boss.shutdownGracefully();
	}
}
