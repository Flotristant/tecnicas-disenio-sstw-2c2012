package servicesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.Message;

import services.POPAccessor;

public class POPAccessorTest {

	@Test
	public void testFetch() {
		try {
			POPAccessor mail = new POPAccessor("pop.google.com", "tecnicasprueba01@gmail.com", "tecnicas01prueba");
			for (Message m : mail.fetchMessages()) {
				System.out.println(m);
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}


}
