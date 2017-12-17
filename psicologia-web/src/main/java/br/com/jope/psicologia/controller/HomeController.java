package br.com.jope.psicologia.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.jope.psicologia.model.FormularioMedico;
import br.com.jope.psicologia.services.UsuarioService;
import br.com.jope.psicologia.view.push.PingPongEventSocketClient;

@Controller
public class HomeController {
	
	private PingPongEventSocketClient client;
    private final String webSocketAddress = "ws://localhost:8080/psicologia-web/cinemaSocket";
	private static List<Integer> countClienteList;
	private FormularioMedico formularioMedico;
	
	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	private void addModelAttribute(Model model) {
		model.addAttribute("countClienteList", countClienteList);
		model.addAttribute("formularioMedico", new FormularioMedico());
	}

	@RequestMapping(value="/enviaMensagem", method = RequestMethod.POST)
	public String enviaMensagem(Model model, @ModelAttribute("formularioMedico") FormularioMedico formularioMedico) {
		this.formularioMedico = formularioMedico;
		addModelAttribute(model);
		try {
			initializeWebSocket();
			String mensagem = "{\"identificador\":\""+ formularioMedico.getIdentificaoCliente()+ "\", \"velocidade\":\"" + formularioMedico.getVelocidade() + "\"}";
			JSONObject jsonObject = new JSONObject(mensagem);
			client.sendMessage(mensagem);
		} catch (URISyntaxException | JSONException e) {
			e.printStackTrace();
		}
		
		return "medico";
	}
	
    private void initializeWebSocket() throws URISyntaxException {
        //ws://localhost:7101/CinemaMonitor/cinemaSocket/
        System.out.println("REST service: open websocket client at " + webSocketAddress);
        client = new PingPongEventSocketClient(new URI(webSocketAddress + "/" + formularioMedico.getIdentificaoCliente()));
        // add listener
        client.addMessageHandler(new PingPongEventSocketClient.MessageHandler() {
            public void handleMessage(String message) {
                System.out.println("messagehandler in REST service - process message "+message);
            }
        });
    }

    private void sendMessageOverSocket(String message) {
        if (client == null) {
            try {
                initializeWebSocket();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        client.sendMessage(message);

    }

    @RequestMapping(value="/ws/mensagem", method = RequestMethod.POST, produces = "text/plain", consumes = "application/json")
    @ResponseBody
    public String postMovieEvent(@RequestBody final String json) {
        sendMessageOverSocket(json);
        return "ok";
    }

    @RequestMapping(value="/ws/mensagem", method = RequestMethod.GET, produces = "text/plain", consumes = "application/json")
    @ResponseBody
    public String getMovieEvent() {
        return "nothing to report from getMovieEvent";
    }
	
}
