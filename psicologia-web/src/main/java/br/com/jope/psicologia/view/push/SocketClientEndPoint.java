package br.com.jope.psicologia.view.push;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import br.com.jope.psicologia.util.Util;
// based on http://stackoverflow.com/questions/26452903/javax-websocket-client-simple-example

@ClientEndpoint
public class SocketClientEndPoint {
	
	private static Logger logger = Logger.getLogger(SocketClientEndPoint.class.getName());
	private Session userSession = null;
	private MessageHandler messageHandler;
	
    public SocketClientEndPoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
        	logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
    	System.out.println("@ClientEndpoint onopen " + userSession.getId());
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
    	System.out.println("@ClientEndpoint onclose " + userSession.getId());
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
    	System.out.println("@ClientEndpoint onmessage " + message);
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     * @param message
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param user
     * @param message
     */
    public void sendMessage(String message) {
    	System.out.println("@ClientEndpoint sendmessage " + message);
    	if(!Util.isEmpty(this.userSession)) {
    		this.userSession.getAsyncRemote().sendText(message);
    	}
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}
