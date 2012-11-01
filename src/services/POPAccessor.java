package services;

import java.util.*;
import javax.mail.*;

public class POPAccessor {
	private Store store;
	
	public POPAccessor (String host, String user, String passwd) throws Exception {
		// Puerto y datos usados en Gmail
		
		Properties prop = new Properties();

		// Deshabilitamos TLS
		prop.setProperty("mail.pop3.starttls.enable", "false");

		// Hay que usar SSL
		prop.setProperty("mail.pop3.socketFactory.class","javax.net.ssl.SSLSocketFactory" );
		prop.setProperty("mail.pop3.socketFactory.fallback", "false");

		// Puerto 995 para conectarse.
		prop.setProperty("mail.pop3.port","995");
		prop.setProperty("mail.pop3.socketFactory.port", "995");
		
		Session session = Session.getInstance(prop);
		session.setDebug(true);

		store = session.getStore("pop3");
		store.connect(host, user, passwd);
	}
	
	public List<Model.Message> fetchMessages() throws Exception {
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		
		Message [] mensajes = folder.getMessages();
		
		// Convertimos de javax.mail.Message a Model.Message
		
		ArrayList<Model.Message> res = new ArrayList<Model.Message>();
		
		for (Message m : mensajes) {
			// solo soportamos mensajes de texto plano
			res.add(new Model.Message(m.getSubject(), (String) m.getContent()));
		}
		
		return res;
	}
}
