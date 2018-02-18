package br.com.jope.psicologia.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FormularioMedico extends AbstractFormulario {

	private static final long serialVersionUID = -183895083944787159L;

	@NotEmpty(message="Preenchimento obrigatório.")
	private String deNome;
	
	@NotEmpty(message="Preenchimento obrigatório.")
	@Email(message="E-mail inválido.")
	private String deLogin;
	
	private String identificaoCliente;
	private String velocidade;
	
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
	public String getIdentificaoCliente() {
		return identificaoCliente;
	}
	public void setIdentificaoCliente(String identificaoCliente) {
		this.identificaoCliente = identificaoCliente;
	}
	public String getVelocidade() {
		return velocidade;
	}
	public void setVelocidade(String velocidade) {
		this.velocidade = velocidade;
	}
	
}
