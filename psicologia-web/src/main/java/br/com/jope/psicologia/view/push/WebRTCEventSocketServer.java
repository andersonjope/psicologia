package br.com.jope.psicologia.view.push;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@ServerEndpoint("/webtrc/{hash}/{direcionamento}")
public class WebRTCEventSocketServer {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @SuppressWarnings("unused")
	@OnMessage
    public String onMessage(String message, Session session, @PathParam("hash") String hash, @PathParam("direcionamento") String direcionamento) {
        try {
            JSONObject jObj = new JSONObject(message);
//            System.out.println("received message from client " + hash + " message: " + message);
            for (Session s : peers) {
                try {
                    s.getBasicRemote().sendText(message);
//                    System.out.println("send message to peer ");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("hash") String hash, @PathParam("direcionamento") String direcionamento) {
//        System.out.println("mediator: opened websocket channel for client " + hash);
        peers.add(session);

        try {
            session.getBasicRemote().sendText(hash);
        } catch (IOException e) {
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("hash") String hash, @PathParam("direcionamento") String direcionamento) {
//        System.out.println("mediator: closed websocket channel for client " + hash);
        peers.remove(session);
    }
}

