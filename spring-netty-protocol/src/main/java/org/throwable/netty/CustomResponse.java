package org.throwable.netty;

import java.io.Serializable;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/7/15 22:44
 */
public class CustomResponse implements Serializable{

	private static final long serialVersionUID = -1;
	private String messageId;
	private int statusCode;
	private String result;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
