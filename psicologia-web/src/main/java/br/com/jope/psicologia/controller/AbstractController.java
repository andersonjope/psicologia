package br.com.jope.psicologia.controller;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jope.psicologia.enumeration.EnumUsuario;
import br.com.jope.psicologia.view.message.Message;
import br.com.jope.psicologia.view.message.MessageType;
import br.com.jope.psicologia.view.push.PingPongEventSocketClient;
import br.com.jope.psicologia.vo.UsuarioVO;

public class AbstractController implements Serializable {

	private static final long serialVersionUID = -4808859477511429595L;

	private final String webSocketAddress = "ws://%s:%s/psicologia-web/pingpong/%s";
	private PingPongEventSocketClient client;
	private List<Message> messages;
	
	protected void initializeWebSocket(HttpServletRequest request, String hashSessao) {
		try {
			String[] parametro = new String[] {request.getServerName(), String.valueOf(request.getServerPort()), hashSessao};
			String url = String.format(webSocketAddress, parametro);
	        //System.out.println("REST service: open websocket client at " + url);
	        
			client = new PingPongEventSocketClient(new URI(url));
	        client.addMessageHandler(new PingPongEventSocketClient.MessageHandler() {
	            public void handleMessage(String message) {
	                //System.out.println("messagehandler in REST service - process message "+message);
	            }
	        });
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    }
	
	@SuppressWarnings("unused")
	protected void notificaCliente(HttpServletRequest request, String hashSessao, Integer velocidade, boolean playStop) {
		try {
			if(client == null) {
				initializeWebSocket(request, hashSessao);
			}
			
			String mensagem = "{\"identificador\":\""+ hashSessao+ "\", \"velocidade\":\"" + velocidade + "\", \"playStop\":\"" + playStop + "\"}";
			JSONObject jsonObject = new JSONObject(mensagem);
			client.sendMessage(mensagem);
		} catch (JSONException e) {
			e.printStackTrace();
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
		if(message == null){
			message = keyMsg;
		}
		if(!multipleMessage){
			messages = new ArrayList<Message>();
		}
		Message messageBean = new Message();
		messageBean.setMessageType(messageType);
		messageBean.setMessage(message);
		messages.add(messageBean);
	}
	
	protected UsuarioVO loadUsuarioLogado(HttpServletRequest request) {
		return (UsuarioVO) request.getSession().getAttribute(EnumUsuario.USUARIO_LOGADO.getDescricao());
	}
	
}
