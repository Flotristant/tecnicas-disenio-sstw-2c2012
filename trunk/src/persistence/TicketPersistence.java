package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import model.Message;
//TODO tests de esta cosa
public class TicketPersistence implements ITicketPersistence {

	private Connection conn;
	private Statement statement;
	private String pathAttach;
	
	private void initialize(String codigoMateria) throws Exception {
		this.pathAttach = codigoMateria + "/Tickets/attachments/";
		
		Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db",codigoMateria)); 
        this.statement = this.conn.createStatement(); 
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TICKETPUBLICO (Titulo text  PRIMARY KEY, Estado text, AyudanteAsignado text, Body text, PathAttach text);");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TICKETPRIVADO (Titulo text, Estado text, AyudanteAsignado text, Body text, PathAttach text, Sender text, PRIMARY KEY(Titulo, Sender));");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ANSWERPUBLICO (Id int PRIMARY KEY AUTOINCREMENT, Contenido text, FOREIGN KEY(Titulo) REFERENCES TICKETPUBLICO(Titulo));");
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ANSWERPRIVADO (Id int PRIMARY KEY AUTOINCREMENT, Contenido text, FOREIGN KEY(Titulo) REFERENCES TICKETPUBLICO(Titulo), FOREIGN KEY(Sender) REFERENCES TICKETPUBLICO(Sender));");
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Sender, GroupNr) VALUES (%d, '%s', %d)", 90117, "caty@hola", null));
	}
	
	@Override
	public Message getUnassignedTickets(String codigoMateria) throws Exception{
		initialize(codigoMateria);
		//TODO ver si se hace o no
		closeStatementAndConnection();
		return null;
		
	}

	@Override
	public void createTicket(Message message, boolean publica, String codigoMateria, String tema, String pathAttach) throws Exception {
		initialize(codigoMateria);
		if (publica)
			this.statement.executeUpdate(String.format("INSERT INTO TICKETPUBLICO VALUES ('%s', 'SIN ASIGNAR', NULL, '%s','%s')", tema, 
					message.getBody(), this.pathAttach + pathAttach));	
		else 
			this.statement.executeUpdate(String.format("INSERT INTO TICKETPRIVADO VALUES ('%s', 'SIN ASIGNAR', NULL, '%s', '%s', '%s')", 
					tema, message.getBody(), this.pathAttach + pathAttach, message.getSender()));	
		closeStatementAndConnection();
	}

	@Override
	public void assignTicket(String codigoMateria, String nameAyudante, String titulo, Message message, boolean publica) throws Exception {
		initialize(codigoMateria);
		if (publica)
			this.statement.executeUpdate(String.format("UPDATE TICKETPUBLICO SET Estado='ASIGNADO', " +
					"AyudanteAsignado='%s' WHERE Titulo='%s'", nameAyudante, titulo));	
		else 
			this.statement.executeUpdate(String.format("UPDATE TICKETPRIVADO SET Estado='ASIGNADO', " +
					"AyudanteAsignado='%s' WHERE Titulo='%s' AND Sender='%d'", nameAyudante, titulo, message.getSender()));
		
		closeStatementAndConnection();
	}

	@Override
	public void associateMessageToTicket(String codigoMateria, String titulo, Message message, boolean publica) throws Exception{
		initialize(codigoMateria);
		if (publica)
			this.statement.executeUpdate(String.format("INSERT INTO ANSWERPUBLICO VALUES (null, '%s', '%s');", message.getBody(), titulo));	
		else 
			this.statement.executeUpdate(String.format("INSERT INTO ANSWERPRIVADO VALUES (null, '%s', '%s', '%s');", message.getBody(), titulo, message.getSender()));
		
		closeStatementAndConnection();
	}

	private void closeStatementAndConnection() throws SQLException {
		this.statement.close();
		this.conn.close();
	}
}
