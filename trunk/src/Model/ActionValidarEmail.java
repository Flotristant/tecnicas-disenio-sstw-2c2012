package Model;

import persistence.mailPersistence;

public class ActionValidarEmail extends ActionRule {

	public boolean validarDireccionEmail(String direccionEmail)
	{
		if(mailPersistence.existsMail(direccionEmail))
			return true;
		return false;
	}
		
}
