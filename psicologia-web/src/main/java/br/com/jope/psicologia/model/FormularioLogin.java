package br.com.jope.psicologia.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FormularioLogin {

	@NotEmpty(message="Preenchimento obrigat�rio.")
	@Email(message="E-mail inv�lido.")
	private String email;
	
	@NotEmpty(message="Preenchimento obrigat�rio.")
	private String senha;
	
	private boolean relembre;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isRelembre() {
		return relembre;
	}

	public void setRelembre(boolean relembre) {
		this.relembre = relembre;
	}
	
}
