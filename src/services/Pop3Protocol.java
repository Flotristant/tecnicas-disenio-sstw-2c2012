package services;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;


import com.sun.mail.pop3.POP3SSLStore;


public class Pop3Protocol extends ReceiverProtocol {

	private Session session;
	
	public Pop3Protocol(String user, String pass, String port, String host) {
		super(user, pass, port, host);
		
        Properties pop3Props = new Properties();
        
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        
        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
        pop3Props.setProperty("mail.pop3.port",  this.port);
        pop3Props.setProperty("mail.pop3.socketFactory.port", this.port);
        
        this.session = Session.getInstance(pop3Props, null);
	}

	@Override
	public  List<Model.Message> receive() throws MessagingException, IOException {
        
		URLName url = new URLName("pop3", this.host, Integer.parseInt(this.port), "",
                this.user, this.pass);
	    Store store = new POP3SSLStore(session, url);
        store.connect();
        
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		
        Message [] mensajes = folder.getMessages();
		
		// Convertimos de javax.mail.Message a Model.Message
		
		ArrayList<Model.Message> res = new ArrayList<Model.Message>();
		
		for (Message m : mensajes) {
			// solo soportamos mensajes de texto plano
			HashMap<String, byte[]> attachs = new HashMap<String, byte[]>();
			String body = "--empty--";
			if (m.isMimeType("text/*")) {
				body = (String) m.getContent();
			} else if (m.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) m.getContent();
				for (int i = 0; i<multipart.getCount(); i++) {
					BodyPart p = multipart.getBodyPart(i);
					boolean plaintext_found = false;
					String filename = p.getFileName();
					if (filename == null) { // cuerpo del mail
						if (p.isMimeType("text/plain")) {
							body = (String) p.getContent();
							plaintext_found = true;
						} else if (p.isMimeType("text/*") && !plaintext_found) {
							body = (String) p.getContent();
						}
					} else { // attach
						String content = (String)p.getContent();
						attachs.put(filename, content.getBytes());
					}
				}
			}
			Model.Message modelmessage = new Model.Message(InternetAddress.toString(m.getFrom()), 
					InternetAddress.toString(m.getRecipients(Message.RecipientType.TO)), m.getSubject(), body);
			
			Address[] ccTo = m.getRecipients(Message.RecipientType.CC);
			if ( ccTo != null ) {
					modelmessage.addCC(InternetAddress.toString(ccTo));
			}
		
			Address[] bccTo = m.getRecipients(Message.RecipientType.BCC);
			if ( bccTo != null ) {
					modelmessage.addCC(InternetAddress.toString(bccTo));
			}
						
			modelmessage.getAttachments().putAll(attachs);
			
			
			res.add(modelmessage);
		}
		
		folder.close(true);
		store.close();		
		return res;
	}
}