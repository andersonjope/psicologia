package br.com.jope.psicologia.model;

import javax.validation.constraints.NotNull;

public class FormularioCriaSessao {

	private Long nuSessao;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Long nuMedico;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Long nuCliente;
	
	public Long getNuSessao() {
		return nuSessao;
	}
	public void setNuSessao(Long nuSessao) {
		this.nuSessao = nuSessao;
	}
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
