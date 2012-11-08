package persistence;

import java.util.Map;

public interface ITpPersistence {

	public void saveTp(String codigoMateria, String sender, Integer tpNumber,
			Map<String, String> attachments); 

}
