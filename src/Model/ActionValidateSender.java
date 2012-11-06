package model;

import persistence.IMailPersistence;
import persistence.MailPersistence;

public class ActionValidateSender extends ActionRule {

	private IMailPersistence mailPersistence;
	private String sender;

	public ActionValidateSender(String sender, IMailPersistence mailPersistence) {
		this.sender = sender;
		this.mailPersistence = mailPersistence;
	}

	public ActionValidateSender(IMailPersistence mailPersistence) {
		this("", mailPersistence);
	}

	@Override
	public boolean execute() {
		return validateSender();
	}
	
	private boolean validateSender() {
		return mailPersistence.existsMail(this.sender);
	}
		
}
