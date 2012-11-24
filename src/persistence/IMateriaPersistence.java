package persistence;

import persistence.exceptions.PersistenceException;

public interface IMateriaPersistence {

	String getCodigoMateria(String email) throws PersistenceException;

	void addMateria(int codigoMateria, String email) throws PersistenceException;
}
