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
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TP (Padron INTEGER, TpNumber INTEGER, PathTp text, PRIMARY KEY(Padron, TpNumber), FOREIGN KEY(Padron) REFERENCES ALUMNO(Padron));");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ALUMNO (Padron INTEGER PRIMARY KEY, Name text, Sender text);");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS GROUPALUMNO (Padron INTEGER, GroupNr INTEGER, PRIMARY KEY(Padron, GroupNr), FOREIGN KEY(Padron) REFERENCES ALUMNO(Padron));");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TICKET (Id INTEGER PRIMARY KEY AUTOINCREMENT, Tipo text, Titulo text, Estado text, MailAyudanteAsignado text, Body text, PathAttach text, Sender text);");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ANSWER (Id INTEGER PRIMARY KEY AUTOINCREMENT, Contenido text, Id_Ticket INTEGER, FOREIGN KEY(Id_Ticket) REFERENCES TICKET(Id));");
	}
	
	protected void closeStatementAndConnection() throws SQLException {
		this.statement.close();
		this.conn.close();
	}
	
	protected void initializeDBMateria() throws SQLException, ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:Materias.db"); 
        this.statement = this.conn.createStatement(); 
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS MATERIAS (CodigoMateria INTEGER PRIMARY KEY, Email text)");
	}
}
