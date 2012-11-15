package persistence;

import persistence.exceptions.PersistenceException;

public interface IMailPersistence {

	boolean existsMail(String codigoMateria, String direccionEmail) throws PersistenceException;

}
