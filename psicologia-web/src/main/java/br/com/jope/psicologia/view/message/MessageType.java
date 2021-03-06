package br.com.jope.psicologia.view.message;

public enum MessageType {
	ERROR("ERROR"), WARNING("WARNING"), SUCCESS("SUCCESS"), INFO("INFO");
	
	private final String descricao;
	
	private MessageType(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}