package br.com.jope.psicologia.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FormularioLogin {

	@NotEmpty(message="{obrigatorio}")
	@Email(message="{email_invalido}")
	private String email;
	
	@NotEmpty(message="{obrigatorio}")
	private String senha;
	
	private boolean relembre;
	
	private boolean recuperaSenha;

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

	public boolean isRecuperaSenha() {
		return recuperaSenha;
	}

	public void setRecuperaSenha(boolean recuperaSenha) {
		this.recuperaSenha = recuperaSenha;
	}
	
}
