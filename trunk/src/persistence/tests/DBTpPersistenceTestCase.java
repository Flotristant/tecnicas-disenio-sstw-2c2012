package persistence.tests;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.DBTpPersistence;

public class DBTpPersistenceTestCase {
	
	private final String codigoMateria = "7510";
	private final String sender = "caty@Hola";
	private Connection conn;
	private Statement statement;
	
	@Before
	public void setUp() throws Exception {
		Class.forName("org.sqlite.JDBC");
		this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", this.codigoMateria)); 
        this.statement = this.conn.createStatement();
        
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS ALUMNO (Padron int PRIMARY KEY, Sender text);");
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Sender) VALUES (%d, '%s');", 90117, this.sender));
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Sender) VALUES (%d, '%s');", 91227, "francisco"));
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Sender) VALUES (%d, '%s');", 91678, "caty@Hola1"));
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Sender) VALUES (%d, '%s');", 91000, "alguien"));
        
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS GROUPALUMNO (Padron int, GroupNr int, PRIMARY KEY(Padron, GroupNr), FOREIGN KEY(Padron) REFERENCES ALUMNO(Padron));");
        this.statement.executeUpdate(String.format("INSERT INTO GROUPALUMNO (Padron, GroupNr) VALUES (%d, %d);", 91227, 1));
        this.statement.executeUpdate(String.format("INSERT INTO GROUPALUMNO (Padron, GroupNr) VALUES (%d, %d);", 91678, 1));
        this.statement.executeUpdate(String.format("INSERT INTO GROUPALUMNO (Padron, GroupNr) VALUES (%d, %d);", 91000, 1));
	}
	
	@Test
	public void testShouldSaveTpCorrectly() throws Exception {
		DBTpPersistence db = new DBTpPersistence();

		db.saveTpInDB(this.codigoMateria, 90117, 1, null);
		
		Assert.assertTrue(db.isTPDelivered(this.codigoMateria, this.sender, 1));
		Assert.assertFalse(db.isTPDelivered(this.codigoMateria, "20", 30));
	}
	
	@Test
	public void testShouldReturnTheStudentsThatHasntGroup() throws Exception{
		DBTpPersistence db = new DBTpPersistence();
		Iterable<Integer> it = db.getPadronesFromGroupOfTheSender(this.codigoMateria, this.sender);
		
		ArrayList<Integer> padrones = new ArrayList<Integer>();
		
		for (Integer padron : it)
			padrones.add(padron);
			
		Assert.assertEquals(1, padrones.size());
		
		Assert.assertEquals("90117", padrones.get(0).toString());
	}
	
	@Test
	public void testShouldReturnAllStudentsThatBelongsInSameGroupOfSender() throws Exception{
		DBTpPersistence db = new DBTpPersistence();
		Iterable<Integer> it = db.getPadronesFromGroupOfTheSender(this.codigoMateria, "caty@Hola1");
		
		ArrayList<Integer> padrones = new ArrayList<Integer>();
		
		for (Integer padron : it)
			padrones.add(padron);
			
		Assert.assertEquals(3, padrones.size());
		
		Assert.assertEquals("91227", padrones.get(0).toString());
		Assert.assertEquals("91678", padrones.get(1).toString());
		Assert.assertEquals("91000", padrones.get(2).toString());
	}
	
	@After
	public void setDown() throws Exception {
		this.statement.close();
		this.conn.close();
		File fichero = new File(this.codigoMateria + ".db");
		fichero.delete();
	}

}
