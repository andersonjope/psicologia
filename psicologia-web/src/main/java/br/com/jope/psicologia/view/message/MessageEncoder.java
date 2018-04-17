package br.com.jope.psicologia.view.message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageEncoder implements Text<MessageWebSocket> {

	private static Gson gson = new Gson();
	
	@Override
	public void init(EndpointConfig config) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public String encode(MessageWebSocket message) throws EncodeException {
		return gson.toJson(message);
	}

}
