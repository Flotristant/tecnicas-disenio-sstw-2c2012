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

import persistence.TpPersistence;

public class TpPersistenceTestCase {

	private final String codigoMateria = "7510";
	private Connection conn;
	private Statement statement;
	
	@Before
	public void setUp() throws Exception {
		this.createAttachments("./testFiles/incoming/");
		Class.forName("org.sqlite.JDBC");
		this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", this.codigoMateria)); 
        this.statement = this.conn.createStatement();
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ALUMNO (Padron int PRIMARY KEY, Sender text, GroupNr int);");
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Sender, GroupNr) VALUES (%d, '%s', %d);", 90116, "francisco", null));
	}
	
	@Test
	public void testShouldMoveCorrectlyTheAttachmentsToCorrectLocation() throws Exception {
		TpPersistence tpPersistence = new TpPersistence();
		HashMap<String, String> attachments = new  HashMap<String, String>();
		attachments.put("attach1", "./testFiles/incoming/");
		attachments.put("attach2", "./testFiles/incoming/");
		
		tpPersistence.saveTp(this.codigoMateria, "francisco", 1, attachments);
		
		File fichero = new File("./" + this.codigoMateria + "/TPs/90116/1/attach1");
		Assert.assertTrue(fichero.exists());
		fichero = new File("./" + this.codigoMateria + "/TPs/90116/1/attach2");
		Assert.assertTrue(fichero.exists());
	}
	
	@Test
	public void testShouldDeleteAttachmentsAfterSaveTp() throws Exception {
		TpPersistence tpPersistence = new TpPersistence();
		HashMap<String, String> attachments = new  HashMap<String, String>();
		attachments.put("attach1", "./testFiles/incoming/");
		attachments.put("attach2", "./testFiles/incoming/");
		
		tpPersistence.saveTp(this.codigoMateria, "francisco", 1, attachments);
		
		File fichero = new File("./7510/TPs/90116/1/attach1");
		Assert.assertTrue(fichero.exists());
		fichero = new File("./7510/TPs/90116/1/attach2");
		Assert.assertTrue(fichero.exists());
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
