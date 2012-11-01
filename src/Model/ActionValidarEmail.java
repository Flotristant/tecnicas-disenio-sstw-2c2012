package Model;

import persistence.IMailPersistence;

public class ActionValidarEmail extends ActionRule {

	private Message message;
	private IMailPersistence mailPersistence;

	public ActionValidarEmail(Message message, IMailPersistence mailPersistence) {
		this.message = message;
		this.mailPersistence = mailPersistence;
	}

	@Override
	public boolean execute() {
		return validarDireccionEmail();
	}
	
	private boolean validarDireccionEmail() {
		return mailPersistence.existsMail(message.getSender());
	}
		
}
