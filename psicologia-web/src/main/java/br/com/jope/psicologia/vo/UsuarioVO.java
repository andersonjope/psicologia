package br.com.jope.psicologia.vo;

import java.io.Serializable;

import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.enumeration.EnumPerfil;

public class UsuarioVO implements Serializable {

	private static final long serialVersionUID = 1085386835854733132L;
	private EnumPerfil enumPerfil;
	private Long nuUsuario;

	public UsuarioVO(Usuario usuario) {
		this.enumPerfil = usuario.getEnumPerfil();
		this.nuUsuario = usuario.getNuUsuario();
	}
	

	public EnumPerfil getEnumPerfil() {
		return enumPerfil;
	}

	public void setEnumPerfil(EnumPerfil enumPerfil) {
		this.enumPerfil = enumPerfil;
	}

	public Long getNuUsuario() {
		return nuUsuario;
	}

	public void setNuUsuario(Long nuUsuario) {
		this.nuUsuario = nuUsuario;
	}
	
}
