package br.com.jope.psicologia.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FormularioCliente implements Serializable {

	private static final long serialVersionUID = 5752885575790773812L;
	
	@NotEmpty(message="Preenchimento obrigatório.")
	private String deNome;
	
	@NotEmpty(message="Preenchimento obrigatório.")
	@Email(message="E-mail inválido.")
	private String deLogin;

	public String getDeNome() {
		return deNome;
	}
	public void setDeNome(String deNome) {
		this.deNome = deNome;
	}
	public String getDeLogin() {
		return deLogin;
	}
	public void setDeLogin(String deLogin) {
		this.deLogin = deLogin;
	}
	
}
