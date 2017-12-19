package br.com.jope.psicologia.view.message;

public enum MessageType {
	ERROR("ERROR"), WARNING("WARNING"), SUCCESS("SUCCESS"), INFO("INFO");
	
	private String descricao;
	
	private MessageType(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}