package persistence;

import persistence.exceptions.PersistenceException;

public interface IMailPersistence {

	boolean existsMail(String direccionEmail) throws PersistenceException;

}
