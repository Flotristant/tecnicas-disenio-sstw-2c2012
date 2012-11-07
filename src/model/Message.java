package model;

import java.util.*;

public class Message {
	private String subject, body;
	private Map<String, byte[]> attachments;
	private String sender;
	private List<String> to;
	private List<String> cc;
	private List<String> bcc;
	
	
	public Message(String from, String to, String subject, String body) {
		this.sender = from;
		this.setTo(to);
		this.subject = subject;
		this.body = body;
		this.attachments = new HashMap<String, byte[]>();
		this.cc = null;
		this.bcc = null;
	}
	
	
	public String getSender() {
		return sender;
	}

	public void setFrom(String from) {
		this.sender = from;
	}
	
	public List<String> getTo() {
		return to;
	}

	private void setTo(String to) {
		this.to = this.splitDirs(to);
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
	
	public void addAttachment(String filename, byte[] data) {
		this.attachments.put(filename, data);
	}
	
	public Map<String, byte[]> getAttachments() {
		return this.attachments;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("Subject: %s\n", this.subject));
		str.append(String.format("Body: %s\n", this.body));
		str.append(String.format("Attachments: %s\n", this.attachments));
		return str.toString();
	}

	private List<String> splitDirs(String str) {
		String[] strs = str.split(",");
		List<String> dirs = new ArrayList<String>();
		for (int i=0; i < strs.length; i++) {
			strs[i]=strs[i].replaceAll("(\\n|\\r|\\t)","");
			strs[i]=strs[i].replaceAll(" " ,"");
			int j = strs[i].indexOf('<');
			if ( j != -1) {
				strs[i]= strs[i].substring(j+1, strs[i].length()-1);
				strs[i]= strs[i].replaceAll(">"," ");
			}
			
			dirs.add(strs[i]);
		}
		return dirs;
	}
	
	public void addCC(String toCC) {
		this.cc = this.splitDirs(toCC);		
	}
	
	public void addBCC(String toBCC) {
		this.bcc = this.splitDirs(toBCC);
	}

	public List<String> getToCC() {
		return this.cc;
	}
	
	public List<String> getToBCC() {
		return this.bcc;
	}
}
