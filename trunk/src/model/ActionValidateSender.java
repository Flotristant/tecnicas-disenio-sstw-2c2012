package model;

import persistence.IMailPersistence;
import persistence.exceptions.PersistenceException;

public class ActionValidateSender extends ActionRule {

	private IMailPersistence mailPersistence;

	public ActionValidateSender(IMailPersistence mailPersistence) {
		this.mailPersistence = mailPersistence;
	}

	@Override
	public void execute() throws Exception {
		if (!validateSender())
			throw new Exception("Sender doesn't belong to this course");
	}
	
	private boolean validateSender() throws PersistenceException {
		return mailPersistence.existsMail(this.message.getSender());
	}

	@Override
	protected void initializeActions(Rule rule) {
	}
		
}
