package services;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;
public abstract class SenderProtocol extends ProtocolMail {
	
	public SenderProtocol(String user, String pass, String port, String host) throws InvalidPortFormatException, InvalidUserFormatException {
		super(user, pass, port, host);
	}

	public abstract void send(List<model.Message> messages) throws AddressException, MessagingException;
}
