package services;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;

import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;


import com.sun.mail.imap.protocol.FLAGS;
import com.sun.mail.pop3.POP3SSLStore;


public class Pop3Protocol extends ReceiverProtocol {

	private Session session;
	private String path_attc;
	
	public Pop3Protocol(String user, String pass, String port, String host, String path_attch) throws InvalidPortFormatException, InvalidUserFormatException {
		super(user, pass, port, host);
		
        Properties pop3Props = new Properties();
        
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        
        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
        pop3Props.setProperty("mail.pop3.port",  this.port);
        pop3Props.setProperty("mail.pop3.socketFactory.port", this.port);
        
        this.session = Session.getInstance(pop3Props, null);
        
        this.path_attc = path_attch;
	}

	@Override
	public  List<model.Message> receive() throws MessagingException, IOException {
        
		URLName url = new URLName("pop3", this.host, Integer.parseInt(this.port), "",
                this.user, this.pass);
	    Store store = new POP3SSLStore(session, url);
        store.connect();
        
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		
        Message [] mensajes = folder.getMessages();
		
		// Convertimos de javax.mail.Message a Model.Message
		
		ArrayList<model.Message> res = new ArrayList<model.Message>();
		
		for (Message m : mensajes) {
			// solo soportamos mensajes de texto plano

			HashMap<String, String> attachs = new HashMap<String, String>();
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
					} else {//atach
						MimeBodyPart mbp = (MimeBodyPart)p;
						mbp.saveFile(this.path_attc + filename);
						attachs.put(filename, this.path_attc);
					}
				}
			}
			model.Message modelmessage = new model.Message(InternetAddress.toString(m.getFrom()), this.user, m.getSubject(), body);
			
			Address[] ccTo = m.getRecipients(Message.RecipientType.CC);
			if ( ccTo != null ) {
					modelmessage.addCC(InternetAddress.toString(ccTo));
			}
		
			Address[] bccTo = m.getRecipients(Message.RecipientType.BCC);
			if ( bccTo != null ) {
					modelmessage.addCC(InternetAddress.toString(bccTo));
			}
			
			modelmessage.addAttachments(attachs);
					
			res.add(modelmessage);
			m.setFlag(Flag.DELETED, true);
		}
		folder.close(true);
		store.close();		
		return res;
	}

	public void setPath_attc(String path_attc) {
		this.path_attc = path_attc;
	}

	public String getPath_attc() {
		return path_attc;
	}
}