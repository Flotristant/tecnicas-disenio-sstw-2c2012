package persistence.tests;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.DBStudentPersistence;
import persistence.exceptions.PersistenceException;

public class DBStudentPersistenceTestCase {

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
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Name, Sender) VALUES (%d, '%s', '%s');", 91227, "sender", "francisco"));
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Name, Sender) VALUES (%d, '%s', '%s');", 91678, "sender", "caty@Hola1"));
        this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Name, Sender) VALUES (%d, '%s', '%s');", 91000, "sender", "alguien"));
        
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS GROUPALUMNO (Padron int, GroupNr int, PRIMARY KEY(Padron, GroupNr), FOREIGN KEY(Padron) REFERENCES ALUMNO(Padron));");
        this.statement.executeUpdate(String.format("INSERT INTO GROUPALUMNO (Padron, GroupNr) VALUES (%d, %d);", 91227, 1));
        this.statement.executeUpdate(String.format("INSERT INTO GROUPALUMNO (Padron, GroupNr) VALUES (%d, %d);", 91678, 1));
	}
	
	@Test
	public void testShouldSaveStudentCorrectly() throws PersistenceException {
		DBStudentPersistence db = new DBStudentPersistence();
		
		db.saveStudent(this.codigoMateria, 91111, "francisco", "francisco@francisco");
		
		Assert.assertTrue(db.validateStudentInCuatrimestre(codigoMateria, 91111));
	}
	
	@Test
	public void testShouldRetriveTrueOrFalseWhenValidateStudentInGroup() throws PersistenceException {
		DBStudentPersistence db = new DBStudentPersistence();
		
		Assert.assertTrue(db.validateStudentInGroup(this.codigoMateria, 90117));
		Assert.assertFalse(db.validateStudentInGroup(this.codigoMateria, 91227));
	}
	
	@Test
	public void testShouldRetriveStudentFromTable() throws PersistenceException {
		DBStudentPersistence db = new DBStudentPersistence();
		
		Assert.assertTrue(db.validateStudentInCuatrimestre(codigoMateria, 90117));
	}
	
	@Test
	public void testShouldDontRetriveAnUnexistentStudentFromTable() throws PersistenceException {
		DBStudentPersistence db = new DBStudentPersistence();
		
		Assert.assertFalse(db.validateStudentInCuatrimestre(codigoMateria, 10000));
	}
	
	@After
	public void setDown() throws Exception {
		this.statement.close();
		this.conn.close();
		File fichero = new File(this.codigoMateria + ".db");
		fichero.delete();
	}
}
