package br.com.jope.psicologia.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.enumeration.EnumUsuario;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.view.message.Message;
import br.com.jope.psicologia.view.message.MessageType;
import br.com.jope.psicologia.view.push.SocketClientEndPoint;
import br.com.jope.psicologia.vo.UsuarioVO;

public class AbstractController implements Serializable {

	private static final long serialVersionUID = -4808859477511429595L;

	private transient Map<String, SocketClientEndPoint> mapClients = Collections.synchronizedMap(new LinkedHashMap<String, SocketClientEndPoint>());
	private transient List<Message> messages;
	
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
    		mapClients.put(hash, client);
    	}else {
    		mapClients.remove(hash);
    		mapClients.put(hash, client);
    	}
	}
	
	SocketClientEndPoint loadMapClient(String hash) {
		return mapClients.containsKey(hash) ? mapClients.get(hash) : null;
	}
	
}
