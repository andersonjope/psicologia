package br.com.jope.psicologia.model;

import javax.validation.constraints.NotNull;

public class FormularioSessao {

	@NotNull(message="Preenchimento obrigatório.")
	private Long nuMedico;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Long nuCliente;
	
	public Long getNuMedico() {
		return nuMedico;
	}
	public void setNuMedico(Long nuMedico) {
		this.nuMedico = nuMedico;
	}
	public Long getNuCliente() {
		return nuCliente;
	}
	public void setNuCliente(Long nuCliente) {
		this.nuCliente = nuCliente;
	}
	
}
