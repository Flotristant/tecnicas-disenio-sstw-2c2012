package persistence;

import java.util.Map;

public class TpPersistence implements ITpPersistence {

	@Override
	public void saveTp(String codigoMateria, String sender, Integer tpNumber,
			Map<String, byte[]> attachments) {
		// TODO guardar tp en BD y guardar attachments en path

	}

}
