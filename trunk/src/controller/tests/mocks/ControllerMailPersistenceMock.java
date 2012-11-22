package controller.tests.mocks;

import persistence.IMailPersistence;

public class ControllerMailPersistenceMock implements IMailPersistence {
	
	private String dirSender;

	@Override
	public boolean existsMail(String codigoMateria, String direccionEmail) {
		this.setDirSender(direccionEmail);
		if (direccionEmail.equals("from@hotmail.com"))
			return true;
		return false;
	}

	public String getDirSender() {
		return dirSender;
	}

	public void setDirSender(String dirSender) {
		this.dirSender = dirSender;
	}

}
