package br.com.jope.psicologia.model;

import javax.validation.Valid;

import br.com.jope.psicologia.entity.Cliente;

public class FormularioCliente extends AbstractFormulario {

	private static final long serialVersionUID = 5752885575790773812L;
	
	@Valid
	private Cliente cliente;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
