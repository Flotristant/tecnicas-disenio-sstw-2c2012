package services;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;


public abstract class ReceiverProtocol extends ProtocolMail {
	public ReceiverProtocol(String user, String pass, String port, String host) {
		super(user, pass, port, host);
	}

	public abstract  List<model.Message> receive() throws MessagingException, IOException;	
}
