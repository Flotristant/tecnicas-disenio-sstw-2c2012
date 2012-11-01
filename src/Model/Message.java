package Model;

import java.util.*;

public class Message {
	private String subject, body;
	private Map<String, byte[]> attachments;
	
	public Message(String subject, String body, Map<String, byte[]> attachments) {
		this.subject = subject;
		this.body = body;
		this.attachments = attachments;
	}
	
	public Message(String subject, String body) {
		this.subject = subject;
		this.body = body;
		this.attachments = new HashMap<String, byte[]>();
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
}
