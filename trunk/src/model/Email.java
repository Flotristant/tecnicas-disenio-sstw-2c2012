package model;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import persistence.exceptions.PersistenceException;

import model.exceptions.InvalidAssociatedProtocolsException;
import services.Pop3Protocol;
import services.SenderProtocol;
import services.ReceiverProtocol;
import controller.ProjectController;;

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

	public void processMail(ProjectController controller) throws MessagingException, IOException, PersistenceException {
		List<model.Message> incomingMessages = this.receiver.receive();
		List<model.Message> answerMessages = controller.processIncoming(incomingMessages);
		if(answerMessages != null ) {
			this.sender.send(answerMessages);
		}
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
	
	public ReceiverProtocol getReceiver() {
		return this.receiver;
	}
	
	public SenderProtocol getSender() {
		return this.sender;
	}
	
}
