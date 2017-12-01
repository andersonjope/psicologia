package br.com.jope.psicologia.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import br.com.jope.psicologia.model.Message;

@EnableScheduling
@Controller
public class GreetingController {

//    @Autowired
//    private SimpMessagingTemplate template;
//
//    @Scheduled(fixedRate = 5000)
//    public void greeting() {
//        try {
//        	Message message = new Message();
//        	message.setFrom("From: " + new Date());
//        	message.setText("Text: " + new Date());
//			Thread.sleep(1000);
//// 			System.out.println("scheduled");
//			this.template.convertAndSend("/topic/messages", message);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//    }

}
