package persistence.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.DBMateriaPersistence;
import persistence.DBStudentPersistence;
import persistence.exceptions.PersistenceException;

public class DBMateriaPersistanceTestCase {

	private final String codigoMateria = "7510";
	private Connection conn;
	private Statement statement;
	private DBMateriaPersistence db;
	
	@Before
	public void setUp() throws Exception {
		this.db = new DBMateriaPersistence();
		
		Class.forName("org.sqlite.JDBC");
		this.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", this.codigoMateria)); 
        this.statement = this.conn.createStatement();
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS MATERIAS (CodigoMateria INTEGER, Descripcion text, Nombre text, Email text, User text, Pass text, Pop3host text, Pop3port INTEGER, Smtphost text, Smtpport INTGER, PRIMARY KEY(CodigoMateria, Email))");
        this.statement.executeUpdate(String.format("INSERT INTO MATERIAS (CodigoMateria, Descripcion, Nombre, Email, User, Pass, Pop3host, Pop3port, Smtphost, Smtpport) VALUES (%d, '%s', '%s', '%s', '%s' ,'%s','%s', %d, '%s',%d);",7510, "Materia de programacion", 
        		"Tecnicas de diseño", "pruebatecnicas@hotmail.com", "mailprueba", "pop3.gmail.com", 587, "smtp.gmail.com",9999));
        
	}
	
	@Test
	public void testShouldSaveMateriaCorrectly() throws PersistenceException {
		
		db.addMateria(2230, "Analisis de la info", "Materia", "an@gmail.com", "dsa", "1234", "live.pop3.com", 223, "live.smtp.com", 1111);
		
		Assert.assertTrue(db.validateMateria("2230"));
	}
	
	
	@Test
	public void testShouldDontRetriveAnUnexistentMateriaFromTable() throws PersistenceException {
		Assert.assertTrue(db.validateMateria("2332"));
	}
	
	
	@After
	public void setDown() throws Exception {
		this.statement.close();
		this.conn.close();
		File fichero = new File(this.codigoMateria + ".db");
		fichero.delete();
	}
}
