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
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		
		Message [] mensajes = folder.getMessages();
		
		// Convertimos de javax.mail.Message a Model.Message
		
		ArrayList<Model.Message> res = new ArrayList<Model.Message>();
		
		for (Message m : mensajes) {
			// solo soportamos mensajes de texto plano
			String body = "--empty--";
			if (m.isMimeType("text/*")) {
				body = (String) m.getContent();
			} else if (m.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) m.getContent();
				for (int i = 0; i<multipart.getCount(); i++) {
					Part p = multipart.getBodyPart(i);
					if (p.isMimeType("text/plain")) {
						body = (String) p.getContent();
						break;
					} else if (p.isMimeType("text/*")) {
						body = (String) p.getContent();
					}
				}
			}
			res.add(new Model.Message(m.getSubject(), body));
		}
		
		return res;
	}
}
