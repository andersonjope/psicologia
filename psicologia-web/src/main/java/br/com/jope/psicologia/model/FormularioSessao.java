package br.com.jope.psicologia.model;

import javax.validation.constraints.NotNull;

public class FormularioSessao {

	private Long nuSessao;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Long nuMedico;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Long nuCliente;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Integer velocidade;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Integer altura;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Integer largura;
	
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
	public Integer getVelocidade() {
		return velocidade;
	}
	public void setVelocidade(Integer velocidade) {
		this.velocidade = velocidade;
	}
	public Integer getAltura() {
		return altura;
	}
	public void setAltura(Integer altura) {
		this.altura = altura;
	}
	public Integer getLargura() {
		return largura;
	}
	public void setLargura(Integer largura) {
		this.largura = largura;
	}
	
}
