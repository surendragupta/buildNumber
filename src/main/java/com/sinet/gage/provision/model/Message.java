package com.sinet.gage.provision.model;

import java.io.Serializable;

/**
 * Common rest service response pojo class
 * 
 * @author Team Gage
 *
 */
public class Message<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private MessageType messageType;
	private String message;
	private Object data;

	public Message() {
	}

	public Message(MessageType messageType, String message, Object data) {
		this.messageType = messageType;
		this.message = message;
		this.data = data;
	}

	public Message(MessageType messageType, String message) {
		this.messageType = messageType;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
