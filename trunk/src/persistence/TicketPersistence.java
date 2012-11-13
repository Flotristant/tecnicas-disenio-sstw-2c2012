package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import model.Message;

public class TicketPersistence implements ITicketPersistence {

	private Connection conn;
	private Statement statement;
	private String pathTp;
	
	private void initialize(String dbName) throws Exception {
		this.pathTp = dbName + "/Tps";
		
		Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db",dbName)); 
        this.statement = this.conn.createStatement(); 
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TICKETPUBLICO (Titulo text  PRIMARY KEY, Estado text, AyudanteAsignado text, Body text, PathAttach text);");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TICKETPRIVADO (Titulo text, Estado text, AyudanteAsignado text, Body text, PathAttach text, Sender text, PRIMARY KEY(Titulo, Sender));");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ANSWERPUBLICO (Id int PRIMARY KEY AUTOINCREMENT, Contenido text, FOREIGN KEY(Titulo) REFERENCES TICKETPUBLICO(Titulo));");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ANSWERPRIVADO (Id int PRIMARY KEY AUTOINCREMENT, Contenido text, FOREIGN KEY(Titulo) REFERENCES TICKETPUBLICO(Titulo), FOREIGN KEY(Sender) REFERENCES TICKETPUBLICO(Sender));");
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Sender, GroupNr) VALUES (%d, '%s', %d)", 90117, "caty@hola", null));
	}
	
	@Override
	public Message getUnassignedTickets(String codigoMateria) throws Exception{
//		initialize(codigoMateria);
//		ResultSet rs = this.statement.executeQuery("SELECT titulo FROM TICKET WHERE Estado = 'SIN ASIGNAR'");
//		if(!rs.next()) return null;
//		
//		rs.close();
//		this.statement.close();
//		this.conn.close();
//		
		return null;
		
	}

	@Override
	public void createTicket(Message message, boolean publica, String codigoMateria, String tema, String pathAttach) throws Exception {
		initialize(codigoMateria);
		if(publica){
			this.statement.executeUpdate(String.format("INSERT INTO TICKETPUBLICO VALUES ('%s', 'SIN ASIGNAR', NULL, '%s','%s')", tema, message.getBody(), pathAttach));	
		}
		else {
			this.statement.executeUpdate(String.format("INSERT INTO TICKETPRIVADO VALUES ('%s', 'SIN ASIGNAR', NULL, '%s', '%s', '%s')", tema, message.getBody(), pathAttach, message.getSender()));	
		}
		
		this.statement.close();
		this.conn.close();
	}

	@Override
	public void assignTicket(Message message, boolean publica, String codigoMateria) {
		
	}

	@Override
	public void associateMessageToTicket(Message message, boolean publica, String codigoMateria){
		
	}

}
