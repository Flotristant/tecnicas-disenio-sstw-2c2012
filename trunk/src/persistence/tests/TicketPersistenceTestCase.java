package persistence.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.TicketPersistence;

public class TicketPersistenceTestCase {

	private Connection conn;
	private Statement statement;
	private final String codigoMateria = "7510";

	@Before
	public void setUp() throws Exception {
		this.createAttachments("./testFiles/incoming/");
		Class.forName("org.sqlite.JDBC");
		this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", this.codigoMateria)); 
        this.statement = this.conn.createStatement();
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS TICKET (Id INTEGER PRIMARY KEY AUTOINCREMENT, Tipo text, Titulo text, Estado text, MailAyudanteAsignado text, Body text, PathAttach text, Sender text);");
	}
	
	@Test
	public void testShouldMoveCorrectlyTheAttachmentsToCorrectLocation() throws Exception {
		TicketPersistence ticketPersistence = new TicketPersistence();
		HashMap<String, String> attachments = new  HashMap<String, String>();
		attachments.put("attach1", "./testFiles/incoming/");
		attachments.put("attach2", "./testFiles/incoming/");
		ticketPersistence.saveTicketAttachments(attachments, this.codigoMateria);
		File fichero = new File("./" + this.codigoMateria + "/Tickets/attachments/1/attach1");
		Assert.assertTrue(fichero.exists());
		fichero = new File("./" + this.codigoMateria + "/Tickets/attachments/1/attach2");
		Assert.assertTrue(fichero.exists());
		
	}
	
	@Test
	public void testShouldDeleteAttachmentsAfterSaveTicketAttach() throws Exception {
		TicketPersistence ticketPersistence = new TicketPersistence();
		HashMap<String, String> attachments = new  HashMap<String, String>();
		attachments.put("attach1", "./testFiles/incoming/");
		attachments.put("attach2", "./testFiles/incoming/");
		
		ticketPersistence.saveTicketAttachments(attachments, this.codigoMateria);
		
		File fichero = new File("./testFiles/incoming/attach1");
		Assert.assertFalse(fichero.exists());
		fichero = new File("./testFiles/incoming/attach2");
		Assert.assertFalse(fichero.exists());
	}
	
	private void createAttachments(String pathIncoming) throws IOException {
		File dir = new File (pathIncoming);
		dir.mkdir();
		File directory = new File(pathIncoming + "attach1");
		directory.createNewFile();
		FileOutputStream out = new FileOutputStream(directory);
		out.write("attach1".getBytes());
		out.close();
		directory = new File(pathIncoming + "attach2");
		directory.createNewFile();
		out = new FileOutputStream(directory);
		out.write("attach2".getBytes());
		out.close();
	}
	
	private void deleteFichero(File dir) {
		File[] ficheros = dir.listFiles();
		for (int x = 0; x < ficheros.length; x++){
			if (ficheros[x].isDirectory())
				this.deleteFichero(ficheros[x]);
			ficheros[x].delete();
		}
		dir.delete();
	}
	
	@After
	public void setDown() throws Exception {
		File fichero = new File(this.codigoMateria + ".db");
		fichero.delete();
		fichero = new File("./testFiles/incoming/");
		this.deleteFichero(fichero);
		fichero = new File("./" + this.codigoMateria);
		this.deleteFichero(fichero);
		this.statement.close();
		this.conn.close();
	}
	
}
