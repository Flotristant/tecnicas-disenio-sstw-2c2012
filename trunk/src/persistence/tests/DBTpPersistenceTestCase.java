package persistence.tests;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import persistence.DBTpPersistence;

public class DBTpPersistenceTestCase {
	
	
	@Test
	public void testShouldSaveTpCorrectly() throws Exception {
		DBTpPersistence db;

		db = new DBTpPersistence();
		db.saveTp("7510", "caty@hola", 1);
		
		Assert.assertTrue(db.isTPDelivered("7510", "caty@hola", 1));
		Assert.assertFalse(db.isTPDelivered("7510", "20", 30));
	}
	
	@After
	public void setDown() {
		File fichero = new File("7510.db");
		fichero.delete();
	}

}
