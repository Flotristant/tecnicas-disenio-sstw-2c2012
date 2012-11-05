package services;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

public class SmtpProtocol extends SenderProtocol {
	
	private Session session;
	
	public SmtpProtocol(String user, String pass, String port, String host) {
		super(user, pass, port, host);
		
		Properties props = new Properties();
		props = System.getProperties();
		
		// Nombre del host de correo,
		props.put("mail.smtp.host",this.host);

		// TLS si est� disponible
		props.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto para envio de correos
		props.setProperty("mail.smtp.port",this.port);

		// Nombre del usuario
		props.setProperty("mail.smtp.user", this.user);

		// Si requiere o no usuario y password para conectarse.
		if (this.pass != null) {
			props.setProperty("mail.smtp.auth", "true");
		}
		
		this.session = Session.getDefaultInstance(props);
		//this.session.setDebug(true);
	}

	@Override
	public void send(List<Model.Message> messages) throws AddressException, MessagingException {
		
		if (messages != null ) {
			Iterator<Model.Message> it= messages.iterator();
		
			while (it.hasNext()) {
			
				Model.Message message = it.next();
				MimeMessage mimemessage = new MimeMessage(session);
		 
				// Quien envia el correo
				mimemessage.setFrom(new InternetAddress(message.getFrom()));

				//TODO: pincha si hay mas de 1 dir TO
				// A quien va dirigido
				mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(message.getTo()));
				
				//TODO:pincha con + de 1 cc
				if ( message.getToCC() != null ) {
					mimemessage.addRecipient(Message.RecipientType.CC, new InternetAddress(message.getToCC()));
				}
		
				mimemessage.setSubject(message.getSubject());

		
				// create the message part 
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(message.getBody());

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
	    
				for (String filename : message.getAttachments().keySet()) {
					messageBodyPart = new MimeBodyPart();
					DataSource source = new ByteArrayDataSource(message.getAttachment(filename), "application/octet-stream");
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(filename);
					multipart.addBodyPart(messageBodyPart);
				}

				// Put parts in message
				mimemessage.setContent(multipart);
			
				Transport t = this.session.getTransport("smtp");
				t.connect(this.user, this.pass);
			
				t.sendMessage(mimemessage, mimemessage.getAllRecipients());
				t.close();
			
			}
		}
	}
}
