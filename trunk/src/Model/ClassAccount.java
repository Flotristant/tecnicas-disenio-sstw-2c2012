package Model;
import java.io.IOException;
import java.util.*;

import javax.mail.MessagingException;
public class ClassAccount {
	private String name, description, code, path_attach;
	private List<Email> account_email;
	
	public ClassAccount(String name, String description, String code, String path_attach){
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
	
	public void processAccount() throws MessagingException, IOException {
		Iterator<Email> it = this.account_email.iterator();
		while (it.hasNext()) {
			Email e= it.next();
			e.processMail();			
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

	public void setPath_attach(String path_attach) {
		this.path_attach = path_attach;
	}

	public String getPath_attach() {
		return path_attach;
	}

}
