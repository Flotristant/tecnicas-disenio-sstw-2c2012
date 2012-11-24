package persistence.tests.mocks;

import persistence.IMateriaPersistence;
import persistence.exceptions.PersistenceException;

public class MateriaPersistenceMock implements IMateriaPersistence {

	@Override
	public String getCodigoMateria(String email) {
		return "7510";
	}

	@Override
	public void addMateria(int codigoMateria, String email)
			throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

}
