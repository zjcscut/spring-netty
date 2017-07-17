package org.throwable.protocol.support;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 21:15
 */
public class NamedThreadFactory implements ThreadFactory {

	private final static AtomicInteger threadNumber = new AtomicInteger(1);

	private final String prefix;

	private final boolean daemon;

	private final ThreadGroup threadGroup;

	public NamedThreadFactory(String prefix) {
		this(prefix, false);
	}

	public NamedThreadFactory() {
		this("netty-threadpool", false);
	}

	public NamedThreadFactory(String prefix, boolean daemon) {
		this.prefix = prefix + "-thread-";
		this.daemon = daemon;
		SecurityManager securityManager = System.getSecurityManager();
		this.threadGroup = (null == securityManager) ? Thread.currentThread().getThreadGroup() : securityManager.getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable r) {
		String name = prefix + threadNumber.getAndIncrement();
		Thread thread = new Thread(threadGroup, r, name);
		thread.setDaemon(daemon);
		return thread;
	}
}
