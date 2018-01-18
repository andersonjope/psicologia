package br.com.jope.psicologia.model;

import javax.validation.constraints.NotNull;

public class FormularioAlteraSessao {

	private Long nuSessao;
	
	@NotNull(message="Preenchimento obrigatório.")
	private Integer velocidade;
	
	private boolean playStop;
	
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
	public boolean isPlayStop() {
		return playStop;
	}
	public void setPlayStop(boolean playStop) {
		this.playStop = playStop;
	}
	
}
