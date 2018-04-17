package br.com.jope.psicologia.view.push;

import java.util.Map;

import javax.websocket.Session;

public class SocketUsuario {

	private Session session;
	private Map<String, String> users;
	
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Map<String, String> getUsers() {
		return users;
	}
	public void setUsers(Map<String, String> users) {
		this.users = users;
	}

	
}
