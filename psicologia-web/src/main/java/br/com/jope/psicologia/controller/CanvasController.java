package br.com.jope.psicologia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CanvasController {
	
	@RequestMapping(value="/iframeCanvas", method = RequestMethod.GET)
	public String cliente(Model model, @RequestParam("time") String time) {
		return "canvas";
	}
}
