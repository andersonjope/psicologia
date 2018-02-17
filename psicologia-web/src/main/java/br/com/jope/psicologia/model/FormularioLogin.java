package br.com.jope.psicologia.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FormularioLogin {

	@NotEmpty(message="Preenchimento obrigatório.")
	@Email(message="E-mail inválido.")
	private String email;
	
	@NotEmpty(message="Preenchimento obrigatório.")
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
