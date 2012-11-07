package model;
import java.io.IOException;
import java.util.*;
import javax.mail.MessagingException;
import services.SenderProtocol;
import services.ReceiverProtocol;


public class Email {
	private String user, pass;
	private SenderProtocol sender;
	private ReceiverProtocol receiver;

	public Email(SenderProtocol sender, ReceiverProtocol receiver) throws Exception {
		if (this.validateProtocols(sender, receiver)) {
			this.setReceiver(receiver);
			this.setSender(sender);
			this.setUser(sender.getUser());
			this.setPassword(sender.getPass());
		}
		else {
			throw new Exception("Protocolos asociados a cuentas distintas");
		}
	}

	private boolean validateProtocols(SenderProtocol s, ReceiverProtocol r) {
		if ((s.getUser() != r.getUser()) || (s.getPass() != r.getPass())) {
			return false;
		}
		else return true;
	}

	public void setSender(SenderProtocol sender) {
		this.sender = sender;
	}
	
	public void setReceiver(ReceiverProtocol receiver) {
		this.receiver=receiver;
	}

	public void processMail() throws MessagingException, IOException {
		MessageProcessor mp = new MessageProcessor();
		List<model.Message> incomingMessages = this.receiver.receive();
		List<model.Message> answerMessages = mp.process(incomingMessages);
		this.sender.send(answerMessages);
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setPassword(String password) {
		this.pass = password;
	}

	public String getPassword() {
		return pass;
	}
	
}
