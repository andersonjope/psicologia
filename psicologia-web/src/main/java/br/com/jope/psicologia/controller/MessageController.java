package br.com.jope.psicologia.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.jope.psicologia.model.Message;
import br.com.jope.psicologia.model.OutputMessage;

@Controller
@RequestMapping("/")
public class MessageController {

	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public OutputMessage send(Message message) {
	    String time = new SimpleDateFormat("HH:mm").format(new Date());
	    return new OutputMessage(message.getFrom(), message.getText(), time);
	}
	
}
