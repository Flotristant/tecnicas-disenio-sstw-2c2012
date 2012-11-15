package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DBPersistence {

	protected Connection conn;
	protected Statement statement;
	
	protected void initialize(String codigoMateria) throws Exception {
		Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db",codigoMateria)); 
        this.statement = this.conn.createStatement(); 
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TP (Padron int, TpNumber int, PathTp text, PRIMARY KEY(Padron, TpNumber), FOREIGN KEY(Padron) REFERENCES ALUMNO(Padron));");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ALUMNO (Padron int PRIMARY KEY, Sender text);");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS GROUPALUMNO (Padron int, GroupNr int, PRIMARY KEY(Padron, GroupNr), FOREIGN KEY(Padron) REFERENCES ALUMNO(Padron));");
	}
	
	protected void closeStatementAndConnection() throws SQLException {
		this.statement.close();
		this.conn.close();
	}
}
