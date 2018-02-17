package br.com.jope.psicologia.enumeration;

public enum EnumUsuario {

	USUARIO_LOGADO("usuario"),;
	
	private final String descricao;
	
	private EnumUsuario(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
