package br.com.jope.psicologia.vo;

import java.io.Serializable;

public class MensagemSessaoVO implements Serializable {

	private static final long serialVersionUID = -5045544473766228405L;
	
	private String deMensagem;
	private String tipoUsuario;
	
	public String getDeMensagem() {
		return deMensagem;
	}
	public void setDeMensagem(String deMensagem) {
		this.deMensagem = deMensagem;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
}
