package br.com.jope.psicologia.controller;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.enumeration.EnumUsuario;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.view.message.Message;
import br.com.jope.psicologia.view.message.MessageType;
import br.com.jope.psicologia.view.push.PingPongEventSocketClient;
import br.com.jope.psicologia.view.push.SocketClientEndPoint;
import br.com.jope.psicologia.view.push.WebRTCEventSocketClient;
import br.com.jope.psicologia.vo.UsuarioVO;

public class AbstractController implements Serializable {

	private static final long serialVersionUID = -4808859477511429595L;
	private static Logger logger = Logger.getLogger(AbstractController.class.getName());

	private static final String WEB_SOCKET_ADDRESS = "ws://%s:%s/psicologia-web/ws/%s";
	private static final String WEBSOCKETADDRESSPINGPONG = "ws://%s:%s/psicologia-web/pingpong/%s";
	private static final String WEBSOCKETADDRESSPSIPAC = "ws://%s:%s/psicologia-web/webtrc/%s";
	private transient Map<String, Set<SocketClientEndPoint>> mapClients = Collections.synchronizedMap(new LinkedHashMap<String, Set<SocketClientEndPoint>>());
	private transient List<Message> messages;
	
	protected void initializeWebSocket(HttpServletRequest request, String hashSessao) {
//		try {
//			String[] parametro = new String[] {request.getServerName(), String.valueOf(request.getServerPort()), hashSessao};
//			String urlPingPong = String.format(WEB_SOCKET_ADDRESS, parametro);
//			SocketClientEndPoint client = new SocketClientEndPoint(new URI(urlPingPong));
//			client.addMessageHandler(new SocketClientEndPoint.MessageHandler() {
//				public void handleMessage(String message) {
//					return;
//				}
//			});
//			
//			addMapClients(hashSessao, client);
//			
//		} catch (URISyntaxException e) {
//			logger.log(Level.SEVERE, e.getMessage());
//		}
    }
	
	protected void initializeWebSocketWebRTC(HttpServletRequest request, String hashSessao) {
//		try {
//			String[] parametro = new String[] {request.getServerName(), String.valueOf(request.getServerPort()), hashSessao};
//			String urlPsiPac= String.format(WEBSOCKETADDRESSPSIPAC, parametro);
//			WebRTCEventSocketClient clientVideoPsiPac = new WebRTCEventSocketClient(new URI(urlPsiPac));
//			clientVideoPsiPac.addMessageHandler(new WebRTCEventSocketClient.MessageHandler() {
//				public void handleMessage(String message) {
//					return;
//				}
//			});				
//		} catch (URISyntaxException e) {
//			logger.log(Level.SEVERE, e.getMessage());
//		}
	}
	
	@SuppressWarnings("unused")
	protected void notificaCliente(HttpServletRequest request, String hashSessao, Integer velocidade, boolean playStop) {
		try {
			Set<SocketClientEndPoint> loadMapClients = loadMapClients(hashSessao);
			if(Util.isEmpty(loadMapClients)) {
				initializeWebSocket(request, hashSessao);
			}
			
			String mensagem = "{\"operacao\":\"pingpong\", \"velocidade\":\"" + velocidade + "\",\"playStop\":\"" + playStop + "\"}";
			JSONObject jsonObject = new JSONObject(mensagem);
			
			if(!Util.isEmpty(loadMapClients)) {
				for (SocketClientEndPoint client : loadMapClients) {
					client.sendMessage(mensagem);
					client.addMessageHandler(new SocketClientEndPoint.MessageHandler() {
						public void handleMessage(String message) {
							return;
						}
					});				
				}				
			}
		} catch (JSONException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
	
	protected void addMessages(Model model, MessageType messageType, boolean multipleMessage, String keyMsg){
		criaMessage(messageType, multipleMessage, keyMsg);
		model.addAttribute("messages", messages);
	}

	protected void addMessages(RedirectAttributes redirectAttributes, MessageType messageType, boolean multipleMessage, String keyMsg){
		criaMessage(messageType, multipleMessage, keyMsg);
		redirectAttributes.addFlashAttribute("messages", messages);
	}
	
	private void criaMessage(MessageType messageType, boolean multipleMessage, String keyMsg) {
		String message = keyMsg;
		if(!multipleMessage){
			messages = new ArrayList<>();
		}
		Message messageBean = new Message();
		messageBean.setMessageType(messageType);
		messageBean.setDeMessage(message);
		messages.add(messageBean);
	}
	
	protected UsuarioVO loadUsuarioLogado(HttpServletRequest request) {
		return (UsuarioVO) request.getSession().getAttribute(EnumUsuario.USUARIO_LOGADO.getDescricao());
	}

	protected void removeCaracter(Usuario usuario) {
		if(!Util.isEmpty(usuario.getCoCPF())) {
			usuario.setCoCPF(Util.removeCaracteres(usuario.getCoCPF()));
		}
		if(!Util.isEmpty(usuario.getCoCep())) {
			usuario.setCoCep(Util.removeCaracteres(usuario.getCoCep()));
		}
		if(!Util.isEmpty(usuario.getCoTelefone())) {
			usuario.setCoTelefone(Util.removeCaracteres(usuario.getCoTelefone()));
		}
	}
	
	void addMapClients(String hash, SocketClientEndPoint client) {
		if(!mapClients.containsKey(hash)) {
    		Set<SocketClientEndPoint> clients = new HashSet<>();
    		clients.add(client);
    		mapClients.put(hash, clients);
    	}else {
    		mapClients.get(hash).add(client);
    	}
	}
	
	Set<SocketClientEndPoint> loadMapClients(String hash) {
		return mapClients.containsKey(hash) ? mapClients.get(hash) : null;
	}
	
}
