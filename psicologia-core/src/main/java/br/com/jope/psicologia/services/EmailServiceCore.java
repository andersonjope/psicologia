package br.com.jope.psicologia.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceCore implements EmailService {

	private static final long serialVersionUID = 6366295459167776881L;
	private static Logger logger = Logger.getLogger(EmailServiceCore.class.getName());

	@Autowired
    private transient JavaMailSender mailSender; 
	
	@Override
	public void enviaEmail(String email, String assunto, String conteudo) {
		try {
			MimeMessagePreparator messagePreparator = getMessagePreparator(email, assunto, conteudo);
			mailSender.send(messagePreparator);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
	
	private MimeMessagePreparator getMessagePreparator(final String email, final String assunto, final String conteudo) {
		return new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
				mimeMessage.setText(conteudo, "UTF-8", "html");
				mimeMessage.setSubject(assunto);
			}
		};
	}

}
