package br.com.jope.psicologia.exception;

public class BussinessException extends Throwable {

	private static final long serialVersionUID = -5850026435649162235L;
	
	private final String codigo;
	private final String mensagem;
	private final String[] mensagens;
	
	public BussinessException(String mensagem){
		this.mensagem=mensagem;
		this.codigo = null;
		this.mensagens = null;
	}
	
	public BussinessException(String codigo,String mensagem){
		this.codigo=codigo;
		this.mensagem=mensagem;
		this.mensagens = null;
	}

	public BussinessException(String codigo,String[] mensagens){
		this.codigo=codigo;
		this.mensagens=mensagens;
		this.mensagem=null;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String[] getMensagens() {
		return mensagens;
	}

}
