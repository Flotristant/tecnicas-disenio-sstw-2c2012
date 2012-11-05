package Model;

import java.util.*;

public class Message {
	private String subject, body, sender;
	private Map<String, byte[]> attachments;
	private String from;
	private String to;
//	private List<String> cc;
//	private List<String> bcc;
	private String cc;
	private String bcc;
	
	public Message(String sender, String subject, String body, Map<String, byte[]> attachments) {
		this.setSender(sender);
		this.subject = subject;
		this.body = body;
		this.attachments = attachments;
		this.cc=null;
		this.bcc=null;
	}
	
	public Message(String from, String to, String subject, String body) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.attachments = new HashMap<String, byte[]>();
//		this.cc = new ArrayList<String>();
//		this.bcc = new ArrayList<String>();
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
	
//	public Message(String sender, String subject, String body) {
//		this(sender, subject, body, new HashMap<String, byte[]>());
//	}
//	
//	public Message(String subject, String body) {
//		this("", subject, body, new HashMap<String, byte[]>());
//	}

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
	
	public Map<String, byte[]> getAttachments() {
		return this.attachments;
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
	
	public void addCC(String toCC) {
		this.cc= toCC;
	//	this.cc.add(toCC);
	}
	
	public void addBCC(String toBCC) {
		this.bcc = toBCC;
	//	this.bcc.add(toBCC);
	}

	
	public String getToCC() {
		return this.cc;
	}
	
	public String getToBCC() {
		return this.bcc;
	}
}
