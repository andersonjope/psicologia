package br.com.jope.psicologia.model;

public class FormularioAlteraSessao {

	private Long nuSessao;
	private Integer nuVelocidadeMovimento;
	private String acao;
	private boolean sessaoIniciada;
	private boolean somAtivo;

	public Long getNuSessao() {
		return nuSessao;
	}

	public void setNuSessao(Long nuSessao) {
		this.nuSessao = nuSessao;
	}

	public Integer getNuVelocidadeMovimento() {
		return nuVelocidadeMovimento;
	}

	public void setNuVelocidadeMovimento(Integer nuVelocidadeMovimento) {
		this.nuVelocidadeMovimento = nuVelocidadeMovimento;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public boolean isSessaoIniciada() {
		return sessaoIniciada;
	}

	public void setSessaoIniciada(boolean sessaoIniciada) {
		this.sessaoIniciada = sessaoIniciada;
	}

	public boolean isSomAtivo() {
		return somAtivo;
	}

	public void setSomAtivo(boolean somAtivo) {
		this.somAtivo = somAtivo;
	}

}
