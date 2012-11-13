package applicationServicesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import applicationServices.DBTPAdapter;


public class DBTPAdapterTest {

	@Test
	public void test() {
		DBTPAdapter db;
		try {
			db = new DBTPAdapter("testdb.sqlite", true);
			db.setTPDelivered(10, 20, 30);
			if (!db.isTPDelivered(10, 20, 30)) {
				fail("No se registro TP entregado correctamente.");
			}
			if (db.isTPDelivered(100, 20, 30)) {
				fail("Se detecta incorrectamente un tp entregado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Excepcion no detectada");
		}
	}

}
