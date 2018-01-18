package br.com.jope.psicologia.model;

public class FormularioAlteraSessaoSom {

	private Long nuSessao;
	
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
