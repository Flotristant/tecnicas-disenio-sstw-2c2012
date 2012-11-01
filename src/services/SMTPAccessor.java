package services;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPAccessor {
	private Session session;
	private String user, passwd;
	
	public SMTPAccessor (String host, String user, String passwd) {
		this.user = user;
		this.passwd = passwd;
		
		Properties props = new Properties();

		// Nombre del host de correo, es smtp.gmail.com
		props.setProperty("mail.smtp.host", host);

		// TLS si est� disponible
		props.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto de gmail para envio de correos
		props.setProperty("mail.smtp.port","587");

		// Nombre del usuario
		props.setProperty("mail.smtp.user", user);

		// Si requiere o no usuario y password para conectarse.
		props.setProperty("mail.smtp.auth", "true");
		
		session = Session.getDefaultInstance(props);
		session.setDebug(true);
	}
	
	public void sendMessage(Model.Message message) throws Exception {
		MimeMessage mimemessage = new MimeMessage(session);
		 
		// Quien envia el correo
		mimemessage.setFrom(new InternetAddress(message.getFrom()));

		// A quien va dirigido
		mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(message.getTo()));
		
		mimemessage.setSubject(message.getSubject());
		mimemessage.setText(message.getBody());
		
		Transport t = session.getTransport("smtp");
		t.connect(this.user, this.passwd);
		
		t.sendMessage(mimemessage, mimemessage.getAllRecipients());
		t.close();
	}
}