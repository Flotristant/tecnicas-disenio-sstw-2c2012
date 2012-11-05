package Model;
import java.io.IOException;
import java.util.*;
import javax.mail.MessagingException;
import services.SenderProtocol;
import services.ReceiverProtocol;


public class Email {
	private String user, pass;
	private SenderProtocol sender;
	private ReceiverProtocol receiver;

	public Email(String user, String password, SenderProtocol sender, ReceiverProtocol receiver) {
		this.setUser(user);
		this.setPassword(password);
		this.setReceiver(receiver);
		this.setSender(sender);
	}

	public void setSender(SenderProtocol sender) {
		this.sender = sender;
	}
	
	public void setReceiver(ReceiverProtocol receiver) {
		this.receiver=receiver;
	}

	public void processMail() throws MessagingException, IOException {
		MessageProcessor mp = new MessageProcessor();
		List<Model.Message> incomingMessages = this.receiver.receive();
		List<Model.Message> answerMessages = mp.process(incomingMessages);
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
