package services;

import java.util.*;
import javax.mail.*;

import com.sun.mail.pop3.POP3SSLStore;

public class POPAccessor {
	private Store store;
	
	public POPAccessor (String host, String user, String passwd) throws Exception {
		// Puerto y datos usados en Gmail
		
        Properties pop3Props = new Properties();
        
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        
        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
        pop3Props.setProperty("mail.pop3.port",  "995");
        pop3Props.setProperty("mail.pop3.socketFactory.port", "995");
        
        URLName url = new URLName("pop3", "pop.gmail.com", 995, "",
                user, passwd);
        
        Session session = Session.getInstance(pop3Props, null);
        store = new POP3SSLStore(session, url);
        store.connect();
	}
	
	public List<Model.Message> fetchMessages() throws Exception {
		return fetchMessages(false);
	}
	
	public List<Model.Message> fetchMessages(boolean delete_from_server) throws Exception {
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
			Model.Message modelmessage = new Model.Message(m.getSubject(), body);
			modelmessage.getAttachments().putAll(attachs);
			res.add(modelmessage);
		}
		
		if (delete_from_server) {
			folder.close(false);
		}
		
		return res;
	}
}
