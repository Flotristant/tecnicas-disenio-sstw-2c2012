package persistence.tests;


import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.DBMateriaPersistence;
import persistence.exceptions.PersistenceException;

public class DBMateriaPersistanceTestCase {

	private final String codigoMateria = "7510";
	private DBMateriaPersistence db;
	
	@Before
	public void setUp() throws Exception {
		this.db = new DBMateriaPersistence();

          
	}
	
	@Test
	public void testShouldSaveMateriaCorrectly() throws PersistenceException {
		
		db.addMateria(2230, "Analisis de la info", "Materia", "an@gmail.com", "dsa", "1234", "live.pop3.com", 223, "live.smtp.com", 1111);
		
		Assert.assertTrue(db.validateMateria("2230"));
	}
	
	
	@Test
	public void testShouldDontRetriveAnUnexistentMateriaFromTable() throws PersistenceException {
		Assert.assertFalse(db.validateMateria("7510"));
	}
	
	
	@After
	public void setDown() throws Exception {
		File fichero = new File(this.codigoMateria + ".db");
		fichero.delete();
		fichero = new File("Materias.db");
		fichero.delete();
	}
}
