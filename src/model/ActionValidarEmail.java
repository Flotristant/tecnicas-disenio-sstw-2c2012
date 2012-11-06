package model;

import persistence.IMailPersistence;

public class ActionValidarEmail extends ActionRule {

	private IMailPersistence mailPersistence;

	public ActionValidarEmail(IMailPersistence mailPersistence) {
		this.mailPersistence = mailPersistence;
	}

	@Override
	public boolean execute() {
		return validarDireccionEmail();
	}
	
	private boolean validarDireccionEmail() {
		return mailPersistence.existsMail(this.message.getSender());
	}
	
	@Override
	protected void initializeActions(Rule rule) {
	}
		
}
