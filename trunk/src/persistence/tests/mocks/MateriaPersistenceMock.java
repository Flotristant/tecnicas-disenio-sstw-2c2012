package persistence.tests.mocks;

import persistence.IMateriaPersistence;
import persistence.exceptions.PersistenceException;

public class MateriaPersistenceMock implements IMateriaPersistence {

	@Override
	public String getCodigoMateria(String email) {
		return "7510";
	}

	@Override
	public void addMateria(int codigoMateria, String nombre, String desc,
			String email, String user, String pass, String pop3host, int pop3port, String smtphost, int smtpport)
			throws PersistenceException {		
	}

	@Override
	public String getGroupMailMateria(String codigoMateria) {
		
		return "grupomateria@gmail.com";
	}

	@Override
	public void addGroupMailMateria(String codigoMateria, String mail)
			throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

}
