package services;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;

public class SmtpProtocol extends SenderProtocol {
	
	private Session session;
	
	public SmtpProtocol(String user, String pass, String port, String host) throws InvalidPortFormatException, InvalidUserFormatException {
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

	public void debugOn() {
		this.session.setDebug(true);
	}
	@Override
	public void send(List<model.Message> messages) throws AddressException, MessagingException {
		
		if (messages != null ) {
			Iterator<model.Message> it= messages.iterator();
		
			while (it.hasNext()) {
			
				model.Message message = it.next();
				MimeMessage mimemessage = new MimeMessage(session);
		 
				// Quien envia el correo
				mimemessage.setFrom(new InternetAddress(message.getSender()));

				// A quien va dirigido
				List<String> to= message.getTo();
				Iterator<String> ite = to.iterator();
				while (ite.hasNext()) {
					String str= ite.next();
					mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(str));
				}
		
				
				if (message.getToCC() != null ) {
					List<String> toCC = message.getToCC();
					ite = toCC.iterator();
					while (ite.hasNext()) {
						String str= ite.next();
						mimemessage.addRecipient(Message.RecipientType.CC, new InternetAddress(str));
					}
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
