package br.com.jope.psicologia.view.push;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@ServerEndpoint("/webtrc/{hash}")
public class WebRTCEventSocketServer {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @SuppressWarnings("unused")
	@OnMessage
    public synchronized String onMessage(String message, Session session, @PathParam("hash") String hash) {
        try {
            JSONObject jObj = new JSONObject(message);
            Thread.sleep(100);
            for (Session s : peers) {
            	s.getBasicRemote().sendText(message);            			
            }				            		
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return message;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("hash") String hash) {
        try {
        	peers.add(session);
        	session.getBasicRemote().sendText(hash);        		
        } catch (IOException e) {
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("hash") String hash) {
        peers.remove(session);
    }
    
    @OnError
    public void onError(Throwable t) {
    	t.printStackTrace();
    }
}

