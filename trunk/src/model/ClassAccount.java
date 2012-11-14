package model;
import java.io.File;
import controller.ProjectController;
import java.io.IOException;
import java.util.*;

import javax.mail.MessagingException;

import model.exceptions.InvalidPathDirectoryException;
public class ClassAccount {
	private String name, description, code, path_attach;
	private List<Email> account_email;
	
	public ClassAccount(String name, String description, String code, String path_attach) throws InvalidPathDirectoryException{
		this.setName(name);
		this.setDescription(description);
		this.setCode(code);
		this.setPath_attach(path_attach);
		this.account_email = new ArrayList<Email>();
	}
	
	public void addEmail(Email email) {
		this.account_email.add(email);
	}
	
	public void removeEmail(String user) {
		Iterator<Email> it = this.account_email.iterator();
		while (it.hasNext()) {
			Email e  = it.next();
			if ( e.getUser()== user ) {
				it.remove();
				return;
			}
		}
	}
	
	public List<String> getEmails() {
		if (this.account_email.isEmpty()) {
			return null;
		}
		else {
			Iterator<Email> it = this.account_email.iterator();
			List<String> emails= new ArrayList<String>();
			while (it.hasNext()) {
				Email e  = it.next();
				emails.add(e.getUser());
			}
			return emails;
		}
	}
	
	public void processAccount(ProjectController controller) throws MessagingException, IOException {
		Iterator<Email> it = this.account_email.iterator();
		while (it.hasNext()) {
			Email e= it.next();
			e.processMail(controller, this.path_attach);			
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setPath_attach(String path_attach) throws InvalidPathDirectoryException {
		File dir = new File(path_attach);
		if (!dir.isDirectory()) {
				throw new InvalidPathDirectoryException();
		}
		this.path_attach = path_attach;
	}

	public String getPath_attach() {
		return path_attach;
	}

}
