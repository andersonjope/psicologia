package br.com.jope.psicologia.model;

import javax.validation.constraints.NotNull;

public class FormularioAlteraSessao {

	private Long nuSessao;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Integer velocidade;
	
	private Integer altura;
	
	private Integer largura;
	
	public Long getNuSessao() {
		return nuSessao;
	}
	public void setNuSessao(Long nuSessao) {
		this.nuSessao = nuSessao;
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
