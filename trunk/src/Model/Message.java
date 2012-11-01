package Model;

import java.util.*;

public class Message {
	private String subject, body, sender;
	private Map<String, byte[]> attachments;
	private String from;
	private String to;
	
	public Message(String sender, String subject, String body, Map<String, byte[]> attachments) {
		this.setSender(sender);
		this.subject = subject;
		this.body = body;
		this.attachments = attachments;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	public Message(String sender, String subject, String body) {
		this(sender, subject, body, new HashMap<String, byte[]>());
	}
	
	public Message(String subject, String body) {
		this("", subject, body, new HashMap<String, byte[]>());
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public byte[] getAttachment(String filename) {
		return this.attachments.get(filename);
	}
	
	public void setAttachment(String filename, byte[] data) {
		this.attachments.put(filename, data);
	}

	public String getSender() {
		return sender;
	}

	private void setSender(String sender) {
		this.sender = sender;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("Subject: %s\n", this.subject));
		str.append(String.format("Body: %s\n", this.body));
		str.append(String.format("Attachments: %s\n", this.attachments));
		return str.toString();
	}
}
