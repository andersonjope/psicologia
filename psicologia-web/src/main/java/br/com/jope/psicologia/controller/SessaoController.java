package br.com.jope.psicologia.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.entity.Sessao;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.model.FormularioSessao;
import br.com.jope.psicologia.services.ClienteService;
import br.com.jope.psicologia.services.MedicoService;
import br.com.jope.psicologia.services.SessaoService;

@Controller
public class SessaoController {

	@Autowired(required=true)
	@Qualifier("medicoService")
	private MedicoService medicoService;
	
	@Autowired(required=true)
	@Qualifier("clienteService")
	private ClienteService clienteService;
	
	@Autowired(required=true)
	@Qualifier("sessaoService")
	private SessaoService sessaoService;
	
	@RequestMapping(value="/iniciarSessao", method = RequestMethod.GET)
	public String iniciarSessao(Model model) {
		try {
			loadDados(model);
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "iniciarSessao";
	}

	private void loadDados(Model model) throws BussinessException {
		List<Medico> medicoList = medicoService.getAll();
		List<Cliente> clienteList = clienteService.getAll();
		model.addAttribute("medicoList", medicoList);
		model.addAttribute("clienteList", clienteList);
		model.addAttribute("formularioSessao", new FormularioSessao());
	}
	
	@RequestMapping(value="/criarSessao", method = RequestMethod.POST)
	public String criarSessao(Model model, @Valid @ModelAttribute("formularioSessao") FormularioSessao formularioSessao, BindingResult result) {
		try {
			if(result.hasErrors()) {
				return "iniciarSessao";
			}
			
			Cliente cliente = new Cliente();
			cliente.setNuCliente(formularioSessao.getNuCliente());
			
			Medico medico = new Medico();
			medico.setNuMedico(formularioSessao.getNuMedico());
			
			Sessao sessao = new Sessao();
			sessao.setMedico(medico);
			sessao.setCliente(cliente);
			sessao.setDhInicioSessao(new Date());
			
			sessaoService.incluir(sessao);
			
			List<Sessao> sessaoList = sessaoService.getAll();
			model.addAttribute("sessaoList", sessaoList);
			loadDados(model);
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "iniciarSessao";
	}
	
}
