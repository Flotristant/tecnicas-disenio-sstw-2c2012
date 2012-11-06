package model;

import persistence.IMailPersistence;

public class ActionValidarEmail extends ActionRule {

	private Message message;
	private IMailPersistence mailPersistence;

	public ActionValidarEmail(Message message, IMailPersistence mailPersistence) {
		this.message = message;
		this.mailPersistence = mailPersistence;
	}

	public ActionValidarEmail(IMailPersistence mailPersistence) {
		this(null, mailPersistence);
	}

	@Override
	public boolean execute() {
		return validarDireccionEmail();
	}
	
	private boolean validarDireccionEmail() {
		return mailPersistence.existsMail(message.getSender());
	}
	
	public void setMessage(Message message) {
		this.message = message;
	}
		
}
