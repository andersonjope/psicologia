package br.com.jope.psicologia.view.push;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.view.message.MessageDecoder;
import br.com.jope.psicologia.view.message.MessageEncoder;
import br.com.jope.psicologia.view.message.MessageWebSocket;

@ServerEndpoint(value="/ws/{hash}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class SocketServerEndPoint {

	private static Logger logger = Logger.getLogger(SocketServerEndPoint.class.getName());
	private static Map<String, Set<Session>> mapPeers = Collections.synchronizedMap(new LinkedHashMap<String, Set<Session>>()); 
	private Session session;
    private static Set<SocketServerEndPoint> socketEndpoints = new CopyOnWriteArraySet<>();
	
	private static Map<String, Set<SocketUsuario>> clients = Collections.synchronizedMap(new LinkedHashMap<String, Set<SocketUsuario>>());

	@OnMessage
    public MessageWebSocket onMessage(MessageWebSocket message, Session session, @PathParam("hash") String hash) {
        try {
        	Thread.sleep(500);
        	synchronized(this) {
        		if(!Util.isEmpty(message.getOperacao()) && message.getOperacao().equals("connection")) {
        			Set<SocketUsuario> loadSocketUsuarios = loadSocketUsuarios(hash);
        			Map<String, String> users = new LinkedHashMap<>();
        			for (SocketUsuario socketUsuario : loadSocketUsuarios) {
        				if(socketUsuario.getSession().isOpen()) {
        					if(!Util.isEmpty(socketUsuario.getUsers())) {
        						for(Map.Entry<String, String> mapUsers : socketUsuario.getUsers().entrySet()) {
        							if(!mapUsers.getKey().equals(message.getUser())) {
        								users.putAll(putUsers(mapUsers.getKey(), socketUsuario));            			            					
        							}
        						}            			
        					}else {
        						users.putAll(putUsers(message.getUser(), socketUsuario));            			
        					}            			
        				}
        			}
        			message.setUsers(users);
        		}
        		
        		broadcast(message, hash);        		
        	}
        } catch (Exception e) {
        	logger.log(Level.SEVERE, e.getLocalizedMessage());
		} 
        return message;
    }

	private Map<String, String> putUsers(String user, SocketUsuario socketUsuario) {
		Map<String, String> users = new LinkedHashMap<>();
		users.put(user, "true");
		socketUsuario.setUsers(users);
		return users;
	}

    @OnOpen
    public void onOpen(Session session, @PathParam("hash") String hash) {
        System.out.println("@ServerEndpoint onopen " + session.getId() + " hash : " + hash);
        Set<SocketUsuario> socketUsuarios = new HashSet<>();
        if(!Util.isEmpty(clients)) {
        	socketUsuarios = loadSocketUsuarios(hash);
        }
        
        SocketUsuario socketUsuario = new SocketUsuario();
        socketUsuario.setSession(session);        
        socketUsuarios.add(socketUsuario);
        clients.put(hash, socketUsuarios);	        
    }

    @OnClose
    public void onClose(Session session, @PathParam("hash") String hash) {
    	System.out.println("@ServerEndpoint onclose " + session.getId() + " hash : " + hash);
    	encerraSessao(session, hash, "close");
    }

	private void encerraSessao(Session session, String hash, String closeOrError) {
		Set<SocketUsuario> loadSocketUsuarios = loadSocketUsuarios(session, hash);
    	if(!Util.isEmpty(loadSocketUsuarios)) {
    		Map<String, String> users = new LinkedHashMap<>();
    		for (SocketUsuario socketUsuario : loadSocketUsuarios) {
    			users.putAll(socketUsuario.getUsers());
			}
    		
    		for (Map.Entry<String, String> maps : users.entrySet()) {
    			maps.setValue("false");
    		}
    		
    		MessageWebSocket message = new MessageWebSocket();
    		message.setOperacao(closeOrError);
        	message.setUsers(users);	
        	broadcast(message, hash);
    		
    		for (Map.Entry<String, Set<SocketUsuario>> maps : clients.entrySet()) {
    			maps.getValue().removeAll(loadSocketUsuarios);
    		}
    	}
	}

    @OnError
    public void onError(Throwable t, Session session, @PathParam("hash") String hash) {
    	encerraSessao(session, hash, "error");
    }
    
    private Set<SocketUsuario> loadSocketUsuarios(Session session, String hash) {
    	Set<SocketUsuario> retorno = new HashSet<>();
		for (Map.Entry<String, Set<SocketUsuario>> maps : clients.entrySet()) {
			if(maps.getKey().equals(hash)) {
				Set<SocketUsuario> socketUsuarios = maps.getValue();
				for (SocketUsuario socketUsuario : socketUsuarios) {
					if(socketUsuario.getSession().equals(session)) {
						retorno.add(socketUsuario);
					}									
				}
			}
		}
		return retorno;
	}
    
    private static Set<SocketUsuario> loadSocketUsuarios(String hash) {
    	for (Map.Entry<String, Set<SocketUsuario>> maps : clients.entrySet()) {
    		if(maps.getKey().equals(hash)) {
    			return maps.getValue();
    		}
    	}
    	return new HashSet<>();
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

    MessageWebSocket createMensagem(String hash, String message) {
    	MessageWebSocket retorno = new MessageWebSocket();
    	retorno.setHash(hash);
    	retorno.setMessage(message);
    	return retorno;
    }
    
    static void broadcast(MessageWebSocket message, String hash) {
    	try {
			Set<SocketUsuario> socketUsuarios = loadSocketUsuarios(hash);
	    	for (SocketUsuario socketUsuario : socketUsuarios) {
    			Session session = socketUsuario.getSession();
				if(session.isOpen()) {
					session.getBasicRemote().sendObject(message);						
				}
			}
    	} catch (IOException | EncodeException e) {
    		logger.log(Level.SEVERE, e.getLocalizedMessage());
    	}
    }
    
    static void broadcast(MessageWebSocket message, Session session) {
    	try {
	    	for (SocketServerEndPoint socketServerEndPoint : socketEndpoints) {
	    		if(!session.equals(socketServerEndPoint.session)) {
	    			synchronized (socketServerEndPoint) {
	    				socketServerEndPoint.session.getBasicRemote().sendObject(message);
	    			}	    			
	    		}
			}
    	} catch (IOException | EncodeException e) {
    		logger.log(Level.SEVERE, e.getLocalizedMessage());
    	}
    }
    
    MessageWebSocket convertJsonToObject(String message) {
    	try {
    		ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(message, MessageWebSocket.class);
		} catch (JsonParseException | JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
}

