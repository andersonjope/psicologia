package br.com.jope.psicologia.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.jope.psicologia.model.FormularioMedico;
import br.com.jope.psicologia.view.push.MovieEventSocketClient;

@Controller
public class HomeController {
	
	private MovieEventSocketClient client;
    private final String webSocketAddress = "ws://localhost:8080/psicologia-web/cinemaSocket";
	private static List<Integer> countClienteList;
	private FormularioMedico formularioMedico;
	
	@RequestMapping(value="/medico", method = RequestMethod.GET)
	public ModelAndView medico(Model model) {
		addModelAttribute(model);
		return new ModelAndView("medico");
	}

	private void addModelAttribute(Model model) {
		model.addAttribute("countClienteList", countClienteList);
		model.addAttribute("formularioMedico", new FormularioMedico());
	}
	
	@RequestMapping(value="/cliente", method = RequestMethod.GET)
	public String cliente(Model model) {
		if(countClienteList == null) {
			countClienteList =new ArrayList<>();
		}
		countClienteList.add(countClienteList.size() + 1);
		System.out.println(countClienteList.size() + 1);
		model.addAttribute("idCliente", countClienteList.size());
		return "cliente";
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
        client = new MovieEventSocketClient(new URI(webSocketAddress + "/" + formularioMedico.getIdentificaoCliente()));
        // add listener
        client.addMessageHandler(new MovieEventSocketClient.MessageHandler() {
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
