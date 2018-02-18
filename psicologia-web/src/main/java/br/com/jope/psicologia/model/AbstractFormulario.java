package br.com.jope.psicologia.model;

import java.io.Serializable;

public class AbstractFormulario implements Serializable {

	private static final long serialVersionUID = -5095164009349204488L;

	private String coCPF;
	private String deEndereco;
	private String coCep;
	private String deCidade;
	private String dePais;
	private String coTelefone;
	private String deNascimento;
	private String deSexo;
	
	public String getCoCPF() {
		return coCPF;
	}
	public void setCoCPF(String coCPF) {
		this.coCPF = coCPF;
	}
	public String getDeEndereco() {
		return deEndereco;
	}
	public void setDeEndereco(String deEndereco) {
		this.deEndereco = deEndereco;
	}
	public String getCoCep() {
		return coCep;
	}
	public void setCoCep(String coCep) {
		this.coCep = coCep;
	}
	public String getDeCidade() {
		return deCidade;
	}
	public void setDeCidade(String deCidade) {
		this.deCidade = deCidade;
	}
	public String getDePais() {
		return dePais;
	}
	public void setDePais(String dePais) {
		this.dePais = dePais;
	}
	public String getCoTelefone() {
		return coTelefone;
	}
	public void setCoTelefone(String coTelefone) {
		this.coTelefone = coTelefone;
	}
	public String getDeNascimento() {
		return deNascimento;
	}
	public void setDeNascimento(String deNascimento) {
		this.deNascimento = deNascimento;
	}
	public String getDeSexo() {
		return deSexo;
	}
	public void setDeSexo(String deSexo) {
		this.deSexo = deSexo;
	}

}
