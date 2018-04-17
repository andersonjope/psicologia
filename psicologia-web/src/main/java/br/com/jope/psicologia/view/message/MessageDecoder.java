package br.com.jope.psicologia.view.message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder.Text;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageDecoder implements Text<MessageWebSocket> {

	private static Gson gson = new Gson();
	
	@Override
	public void init(EndpointConfig config) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public MessageWebSocket decode(String s) throws DecodeException {
		return gson.fromJson(s, MessageWebSocket.class);
	}

	@Override
	public boolean willDecode(String s) {
		return (s != null);
	}

}
