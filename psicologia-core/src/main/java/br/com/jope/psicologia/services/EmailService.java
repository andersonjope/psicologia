package br.com.jope.psicologia.services;

import java.io.Serializable;

public interface EmailService extends Serializable{

	void enviaEmail(String email, String assunto, String conteudo);
	
}
