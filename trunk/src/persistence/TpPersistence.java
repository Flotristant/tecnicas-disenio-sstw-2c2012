package persistence;

import java.util.Map;

import services.SQLiteAccessor;

public class TpPersistence implements ITpPersistence {
	
	private SQLiteAccessor db;
	
	@Override
	public void saveTp(String codigoMateria, String sender, Integer tpNumber,
			Map<String, String> attachments) {
		
//			db.update(String.format("INSERT INTO TP (Subject, StudentNr, TPNr) VALUES (%d, %d, %d)", subject, studentnr, TPnr));
		}


}

