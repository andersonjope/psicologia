package br.com.jope.psicologia.view.push;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.com.jope.psicologia.controller.AbstractController;
import br.com.jope.psicologia.util.Util;

@ServerEndpoint("/pingpong/{hash}")
public class PingPongEventSocketServer {

	private static Logger logger = Logger.getLogger(AbstractController.class.getName());
    private static Map<String, Set<Session>> mapPeers = Collections.synchronizedMap(new LinkedHashMap<String, Set<Session>>()); 

    @SuppressWarnings("unused")
	@OnMessage
    public String onMessage(String message, Session session, @PathParam("hash") String hash) throws IOException {
        try {
			JSONObject jObj = new JSONObject(message);
        	Set<Session> peers = loadMapPeers(hash);
        	if(!Util.isEmpty(peers)) {
        		for (Session s : peers) {
        			s.getBasicRemote().sendText(message);
        		}        		
        	}
        } catch (JSONException e) {
        	logger.log(Level.SEVERE, e.getMessage());
        }
        return message;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("hash") String hash) {
        try {
        	addMapPeers(hash, session);
            session.getBasicRemote().sendText(hash);
        } catch (IOException e) {
        	logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("hash") String hash) {
    	removeMapPeers(session, hash);
    }
    
    void addMapPeers(String hash, Session session) {
    	if(!mapPeers.containsKey(hash)) {
    		Set<Session> peers = new HashSet<>();
    		peers.add(session);
    		mapPeers.put(hash, peers);
    	}else {
    		mapPeers.get(hash).add(session);
    	}
    }
    
    void removeMapPeers(Session session, String hash) {
    	if(mapPeers.containsKey(hash)) {
    		mapPeers.get(hash).remove(session);
    	}
    }
    
    Set<Session> loadMapPeers(String hash) {
		return mapPeers.containsKey(hash) ? mapPeers.get(hash) : null;
    }
    
}

