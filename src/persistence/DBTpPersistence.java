package persistence;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DBTpPersistence extends DBPersistence {
	
	public void saveTpInDB(String codigoMateria, Integer padron, Integer tpNumber, String pathTp) throws Exception {
		this.initialize(codigoMateria);
        
        this.statement.executeUpdate(String.format("INSERT INTO TP (Padron, TpNumber, PathTp) VALUES (%d, %d, '%s')", 
        		padron, tpNumber, pathTp));
		
        this.closeStatementAndConnection();
	}
	
	public Iterable<Integer> getPadronesFromGroupOfTheSender(String codigoMateria, String sender) throws Exception {
		this.initialize(codigoMateria);
		
		ResultSet rs = this.statement.executeQuery(String.format("SELECT A1.Padron FROM ALUMNO A1 WHERE A1.Sender='%s'" +
				" OR Padron IN (SELECT Padron FROM GROUPALUMNO WHERE GroupNr IN (SELECT GA.GroupNr FROM GROUPALUMNO GA, " +
				"ALUMNO A WHERE A.Sender='%s' AND A.Padron = GA.Padron));", sender, sender));
		
		ArrayList<Integer> padrones = new ArrayList<Integer>();
		while (rs.next())
			padrones.add(rs.getInt("padron"));
		
		rs.close();
		this.closeStatementAndConnection();
		return padrones;
	}

	public boolean isTPDelivered(String codigoMateria, String sender, Integer tpNumber)
			throws Exception {
		this.initialize(codigoMateria);
		ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM TP WHERE TpNumber=%d AND Padron " +
				"IN (SELECT Padron FROM ALUMNO WHERE Sender = '%s');", tpNumber, sender));
		
		rs.next();
		Integer count = Integer.valueOf(rs.getString(1));
		
		rs.close();
		this.closeStatementAndConnection();
		return count == 1;
	}
}