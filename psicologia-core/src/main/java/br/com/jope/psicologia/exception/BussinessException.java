package br.com.jope.psicologia.exception;

public class BussinessException extends Throwable {

	private static final long serialVersionUID = -5850026435649162235L;
	
	private String codigo;
	private String mensagem;
	private String[] mensagens;
	
	public BussinessException(String mensagem){
		this.mensagem=mensagem;
	}
	
	public BussinessException(String codigo,String mensagem){
		this.codigo=codigo;
		this.mensagem=mensagem;
	}

	public BussinessException(String codigo,String[] mensagens){
		this.codigo=codigo;
		this.mensagens=mensagens;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String[] getMensagens() {
		return mensagens;
	}

	public void setMensagens(String[] mensagens) {
		this.mensagens = mensagens;
	}
	
}
