package br.com.jope.psicologia.view.message;

public class Message {

	private MessageType messageType;
	private String message;
	
	public final MessageType getMessageType() {
		return messageType;
	}
	public final void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	public final String getMessage() {
		return message;
	}
	public final void setMessage(String message) {
		this.message = message;
	}
	
}
