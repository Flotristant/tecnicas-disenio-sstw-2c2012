package persistence;

import java.sql.ResultSet;
import java.util.Map;

import applicationServices.ITPAdapter;

import services.SQLiteAccessor;
import services.SQLiteAccessor.QueryHandler;

public class DBTpPersistence implements ITpPersistence {
	
	private SQLiteAccessor db;
	
	public DBTpPersistence() {
		
	}
	
	public void close() throws Exception {
		db.close();
	}
	
	/** no se si esta bien este coso por ahi hay que sacarlo
	 * 
	 */
	@Override
	public void initialize(String dbname, boolean cleardb) throws Exception {
		this.db = new SQLiteAccessor(dbname);
		if (cleardb) {
			this.db.execute("DROP TABLE IF EXISTS TP;");
		}
		this.db.execute("CREATE TABLE IF NOT EXISTS TP (CodigoMateria text, Padron int, TpNumber int, PathAttachments text);");
	}
	
	@Override
	public void saveTp(String codigoMateria, String sender, Integer tpNumber,
			Map<String, String> attachments) {
			//TODO ver que path mandar, si mandar un path o que !!!!
			String pathAttachments = ""; 
			try {
				//buscar padron en base a sender
				db.update(String.format("INSERT INTO TP (CodigoMateria, Padron, TpNumber, PathAttachments) VALUES ")); //(%s, %d, %d, %s)", codigoMateria, padron,tpNumber,pathAttachments));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	@Override
	public boolean isTPDelivered(String codigoMateria, String sender, Integer tpNumber)
			throws Exception {
		//buscar padron int padron = 
		String sql = String.format("SELECT padron FROM Alumno WHERE Sender = %d", sender);
		System.out.println(sql);
		QueryHandler q = db.query(sql);
		ResultSet rs = q.getResultSet();
		int padron = rs.getInt(0);
		
		sql = String.format("SELECT * FROM TP WHERE CodigoMateria=%s and Padron=%d and TpNumber=%d", codigoMateria, padron, tpNumber);
		System.out.println(sql);
		q = db.query(sql);
		rs = q.getResultSet();
		int count = 0;
		rs.next();
		while (!rs.isAfterLast()) {
			count++;
			rs.next();
		}
		return count == 1;
	}
}