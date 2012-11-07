package model;
import java.io.IOException;
import java.util.*;
import javax.mail.MessagingException;

import model.exceptions.InvalidAssociatedProtocolsException;
import services.SenderProtocol;
import services.ReceiverProtocol;


public class Email {
	private String user, pass;
	private SenderProtocol sender;
	private ReceiverProtocol receiver;

	public Email(SenderProtocol sender, ReceiverProtocol receiver) throws InvalidAssociatedProtocolsException {
		this.validateProtocols(sender, receiver);
		this.setReceiver(receiver);
		this.setSender(sender);
		this.setUser(sender.getUser());
		this.setPassword(sender.getPass());
	}

	private void validateProtocols(SenderProtocol s, ReceiverProtocol r) throws InvalidAssociatedProtocolsException {
		if ((s.getUser() != r.getUser()) || (s.getPass() != r.getPass())) {
			throw new InvalidAssociatedProtocolsException();
		}
	}

	public void setSender(SenderProtocol sender) {
		this.sender = sender;
	}
	
	public void setReceiver(ReceiverProtocol receiver) {
		this.receiver=receiver;
	}

	public void processMail(String working_path) throws MessagingException, IOException {
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
