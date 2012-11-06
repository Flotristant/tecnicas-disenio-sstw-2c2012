package model;

import persistence.IMailPersistence;

public class ActionValidateSender extends ActionRule {

	private IMailPersistence mailPersistence;

	public ActionValidateSender(IMailPersistence mailPersistence) {
		this.mailPersistence = mailPersistence;
	}

	@Override
	public boolean execute() {
		return validateSender();
	}
	
	private boolean validateSender() {
		return mailPersistence.existsMail(this.message.getSender());
	}

	@Override
	protected void initializeActions(Rule rule) {
	}
		
}
