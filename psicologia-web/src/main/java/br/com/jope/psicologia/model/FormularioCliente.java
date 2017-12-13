package br.com.jope.psicologia.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FormularioCliente implements Serializable {

	private static final long serialVersionUID = 5752885575790773812L;
	
	@NotEmpty(message="Preenchimento obrigat�rio.")
	private String deNome;
	
	@NotEmpty(message="Preenchimento obrigat�rio.")
	@Email(message="E-mail inv�lido.")
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
