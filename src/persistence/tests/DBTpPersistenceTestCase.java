package persistence.tests;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import persistence.DBTpPersistence;

import applicationServices.DBTPAdapter;

public class DBTpPersistenceTestCase {
	
	
	@Test
	public void testShouldSaveTpCorrectly() throws Exception {
		DBTpPersistence db;

		db = new DBTpPersistence();
		db.saveTp("7510", "caty@hola", 1);
		if (!db.isTPDelivered("7510", "caty@hola", 1)) {
			fail("No se registro TP entregado correctamente.");
		}
		if (db.isTPDelivered("100", "20", 30)) {
			fail("Se detecta incorrectamente un tp entregado.");
		}
	}
	
	@After
	public void setDown() {
		File fichero = new File("7510.db");
		fichero.delete();
	}

}
