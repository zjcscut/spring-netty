package org.throwable.netty;

import java.io.Serializable;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 22:30
 */
public class CustomRequest implements Serializable {
	private static final long serialVersionUID = -1;
	private String messageId;

	public CustomRequest() {
	}

	public CustomRequest(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
