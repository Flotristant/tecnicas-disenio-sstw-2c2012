package model;
import controller.ProjectController;
import persistence.DBMateriaPersistence;
import java.io.IOException;
import java.util.*;

import javax.mail.MessagingException;

import persistence.exceptions.PersistenceException;
public class ClassAccount {
	private String name, description, code, emailGrupo;
	private List<Email> account_email;
	
	public ClassAccount(String name, String description, String code, String emailgrupo) {
		this.setName(name);
		this.setDescription(description);
		this.setCode(code);
		this.emailGrupo = emailgrupo;
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
	
	public void processAccount(ProjectController controller) throws MessagingException, IOException, PersistenceException {
		Iterator<Email> it = this.account_email.iterator();
		while (it.hasNext()) {
			Email e= it.next();
			e.processMail(controller);			
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
	
	public void persists() throws NumberFormatException, PersistenceException {
		DBMateriaPersistence db = new DBMateriaPersistence();
		Iterator<Email> it = this.account_email.iterator();
		while (it.hasNext()) {
			Email e = it.next();
			db.addMateria(Integer.parseInt(this.getCode()), this.getName(), this.getDescription(), e.getUser(), e.getUser(), e.getPassword(), e.getReceiver().getHost(),Integer.parseInt(e.getReceiver().getPort()), e.getSender().getHost(), Integer.parseInt(e.getSender().getPort()));
		}
	}
}
