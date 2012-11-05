package services;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
public abstract class SenderProtocol extends ProtocolMail {
	
	public SenderProtocol(String user, String pass, String port, String host) {
		super(user, pass, port, host);
	}

	public abstract void send(List<Model.Message> messages) throws AddressException, MessagingException;
}
