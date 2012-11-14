package persistence;

import java.util.Map;

import persistence.exceptions.PersistenceException;

public interface ITpPersistence {

	public void saveTp(String codigoMateria, String sender, Integer tpNumber, Map<String, String> attachments) throws PersistenceException;

}
