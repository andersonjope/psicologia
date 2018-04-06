package br.com.jope.psicologia.view.message;

public class Message {

	private MessageType messageType;
	private String deMessage;
	
	public final MessageType getMessageType() {
		return messageType;
	}
	public final void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	public final String getDeMessage() {
		return deMessage;
	}
	public final void setDeMessage(String message) {
		this.deMessage = message;
	}
	
}
