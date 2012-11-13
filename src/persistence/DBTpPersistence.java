package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBTpPersistence {
	
	private Connection conn;
	private Statement statement;
	private String pathTp;
	
	public DBTpPersistence() {
	}


	private void initialize(String codigoMateria) throws Exception {
		this.pathTp = codigoMateria + "/Tps";
		
		Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db",codigoMateria)); 
        this.statement = this.conn.createStatement(); 
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TP (Padron int, TpNumber int, PathTp text, PRIMARY KEY(Padron, TpNumber), FOREIGN KEY(Padron) REFERENCES ALUMNO(Padron));");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ALUMNO (Padron int PRIMARY KEY, Sender text, GroupNr int);");
	}
	
	public void saveTpInDB(String codigoMateria, Integer padron, Integer tpNumber, String pathTp) throws Exception {
			
		initialize(codigoMateria);
		//despues sacar, solo para la prueba
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Sender, GroupNr) VALUES (%d, '%s', %d)", 90117, "caty@hola", null));
        
        this.statement.executeUpdate(String.format("INSERT INTO TP (Padron, TpNumber, PathTp) VALUES (%d, %d, '%s')", padron, tpNumber, pathTp));
		
		closeStatementAndConnection();
	}
	
	public Iterable<Integer> getPadronesFromGroupOfTheSender(String codigoMateria, String sender) throws Exception {
		initialize(codigoMateria);
		
		ResultSet rs = this.statement.executeQuery(String.format("SELECT GroupNr FROM ALUMNO WHERE Sender='%s' AND GroupNr IS NOT NULL", sender));
		ResultSet rs2;
		if(!rs.next()){
			rs2 = this.statement.executeQuery(String.format("SELECT Padron FROM ALUMNO WHERE Sender='%s'", sender));
		}
		else{
			rs2 = this.statement.executeQuery(String.format("SELECT Padron FROM ALUMNO WHERE GroupNr = '%s'", rs.getString("GroupNr")));
		}
		
		ArrayList<Integer> padrones = new ArrayList<Integer>();
		while (rs2.next()){
			padrones.add(rs.getInt("padron"));
		}
		rs.close();
		rs2.close();
		closeStatementAndConnection();
		return padrones;
	}

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
		closeStatementAndConnection();
		
		return count == 1;
	}
	
	private void closeStatementAndConnection() throws SQLException {
		this.statement.close();
		this.conn.close();
	}
	
}