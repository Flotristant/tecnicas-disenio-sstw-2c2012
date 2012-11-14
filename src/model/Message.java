package model;

import java.util.*;

public class Message {
	private String subject, body;
	private HashMap<String, String> attachments;
	private String sender;
	private String to;
	private List<String> cc;
	private List<String> bcc;
	
	
	public Message(String from, String to, String subject, String body) {
		this.sender = from;
		this.setTo(to);
		this.subject = subject;
		this.body = body;
		this.attachments = null;
		this.cc = null;
		this.bcc = null;
	}
	
	
	public String getSender() {
		return sender;
	}

	public void setSender(String from) {
		this.sender = from;
	}
	
	public String getTo() {
		return to;
	}

	private void setTo(String to) {
		this.to = to;
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
	
	public boolean isWithAttachments() {
		if (this.attachments!= null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addAttachments(HashMap<String, String> attach) {
		this.attachments = attach;
	}
	
	public HashMap<String, String> getAttachments() {
		return this.attachments;
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
	
	public List<String> getToCC() {
		return this.cc;
	}
	
	public void addBCC(String toBCC) {
		this.bcc = this.splitDirs(toBCC);
	}
		
	public List<String> getToBCC() {
		return this.bcc;
	}

}
