package persistence;

import java.util.Map;

public interface ITpPersistence {

	public void saveTp(String codigoMateria, String sender, Integer tpNumber) throws Exception;

	boolean isTPDelivered(String codigoMateria, String sender, Integer tpNumber)
			throws Exception;

}
