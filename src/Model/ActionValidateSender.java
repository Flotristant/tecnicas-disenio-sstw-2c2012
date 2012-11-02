package Model;

import persistence.IMailPersistence;

public class ActionValidateSender extends ActionRule {

	private IMailPersistence mailPersistence;
	private String sender;

	public ActionValidateSender(String sender, IMailPersistence mailPersistence) {
		this.sender = sender;
		this.mailPersistence = mailPersistence;
	}

	@Override
	public boolean execute() {
		return validateSender();
	}
	
	private boolean validateSender() {
		return mailPersistence.existsMail(this.sender);
	}
		
}
