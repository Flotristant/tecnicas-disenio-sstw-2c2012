package persistence.tests;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.DBMailPersistence;
import persistence.exceptions.PersistenceException;

public class DBMailPersistenceTestCase {

	private final String codigoMateria = "7510";
	private final String sender = "caty@Hola";
	private Connection conn;
	private Statement statement;
	
	@Before
	public void setUp() throws Exception {
		Class.forName("org.sqlite.JDBC");
		this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", this.codigoMateria)); 
        this.statement = this.conn.createStatement();
        
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ALUMNO (Padron int PRIMARY KEY, Name text, Sender text);");
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Name, Sender) VALUES (%d, '%s', '%s');", 90117, "sender", this.sender));
	}
	
	@Test
	public void testShouldReturnTrueOrFalseWhenCorrespondWhenValidateMail() throws PersistenceException {
		DBMailPersistence db = new DBMailPersistence();
		
		Assert.assertTrue(db.existsMail(this.codigoMateria, this.sender));
		Assert.assertFalse(db.existsMail(this.codigoMateria, "francisco@soler"));
	}
	
	
	@After
	public void setDown() throws Exception {
		this.statement.close();
		this.conn.close();
		File fichero = new File(this.codigoMateria + ".db");
		fichero.delete();
	}
}
