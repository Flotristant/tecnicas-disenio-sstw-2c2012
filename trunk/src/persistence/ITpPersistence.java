package persistence;

import java.util.Map;

public interface ITpPersistence {

	public void saveTp(String codigoMateria, String sender, Integer tpNumber,
			Map<String, String> attachments);

	boolean isTPDelivered(String codigoMateria, String sender, Integer tpNumber)
			throws Exception;

	void initialize(String dbname, boolean cleardb) throws Exception; 

}
