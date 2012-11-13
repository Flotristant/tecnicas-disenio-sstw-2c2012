package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTpPersistence implements ITpPersistence {
	
	private Connection conn;
	private Statement statement;
	private String pathTp;
	
	public DBTpPersistence() {
		
	}


	private void initialize(String dbName) throws Exception {
		this.pathTp = dbName + "/Tps";
		
		Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db",dbName)); 
        this.statement = this.conn.createStatement(); 
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TP (Padron int, TpNumber int, PathTp text, PRIMARY KEY(Padron, TpNumber), FOREIGN KEY(Padron) REFERENCES ALUMNO(Padron));");
        
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ALUMNO (Padron int PRIMARY KEY, Sender text, GroupNr int);");
	}
	
	@Override
	public void saveTp(String codigoMateria, String sender, Integer tpNumber) throws Exception {
			
		initialize(codigoMateria);
		//despues sacar
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Sender, GroupNr) VALUES (%d, '%s', %d)", 90117, "caty@hola", null));

		
		ResultSet rs = this.statement.executeQuery(String.format("SELECT GroupNr FROM ALUMNO WHERE Sender='%s' AND GroupNr IS NOT NULL", sender));
		ResultSet rs2;
		if(!rs.next()){
			rs2 = this.statement.executeQuery(String.format("SELECT Padron FROM ALUMNO WHERE Sender='%s'", sender));
		}
		else{
			rs2 = this.statement.executeQuery(String.format("SELECT Padron FROM ALUMNO WHERE GroupNr = '%s'", rs.getString("GroupNr")));
		}
		while (rs2.next()){
			int padron = rs.getInt("padron");
			String pathTp = this.pathTp + "/" + tpNumber.toString() + "/" + padron;
			this.statement.executeUpdate(String.format("INSERT INTO TP (Padron, TpNumber, PathTp) VALUES (%d, %d, '%s')", padron, tpNumber, pathTp));
		}
		rs.close();
		rs2.close();
		this.statement.close();
		this.conn.close();
	}

	@Override
	public boolean isTPDelivered(String codigoMateria, String sender, Integer tpNumber)
			throws Exception {
		initialize(codigoMateria);
		String sql = String.format("SELECT padron FROM Alumno WHERE Sender = '%s'", sender);
		ResultSet rs = this.statement.executeQuery(sql);
		if(!rs.next()) return false;
		
		int padron = rs.getInt("padron");
		sql = String.format("SELECT * FROM TP WHERE Padron=%d and TpNumber=%d", padron, tpNumber);
		rs = this.statement.executeQuery(sql);
		int count = 0;
		rs.next();
		while (!rs.isAfterLast()) {
			count++;
			rs.next();
		}
		rs.close();
		this.statement.close();
		this.conn.close();
		
		return count == 1;
	}
	
}