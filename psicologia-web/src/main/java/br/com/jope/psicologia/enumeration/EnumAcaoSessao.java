package br.com.jope.psicologia.enumeration;

public enum EnumAcaoSessao {

	INICIAR("1"),
	PAUSAR("2"),
	ENCERRAR("3"),
	AUMENTAR("4"),
	DIMINUIR("5"),
	SOM_ATIVO("6"),
	SOM_MUDO("7"),
	;
	
	private final String codigo;
	
	private EnumAcaoSessao(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public static EnumAcaoSessao loadCodigo(String codigo) {
		for(EnumAcaoSessao acaoSessao : values()) {
			if(acaoSessao.getCodigo().equals(codigo)) {
				return acaoSessao;
			}
		}
		return null;
	}
	
}
