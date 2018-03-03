package br.com.jope.psicologia.model;

import java.io.Serializable;

public class AbstractFormulario implements Serializable {

	private static final long serialVersionUID = -5095164009349204488L;

	private String deNascimento;
	
	public String getDeNascimento() {
		return deNascimento;
	}
	public void setDeNascimento(String deNascimento) {
		this.deNascimento = deNascimento;
	}

}
