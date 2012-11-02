package servicesTests;


import static org.junit.Assert.*;

import org.junit.Test;

import Model.Message;

import services.SMTPAccessor;

public class SMTPAccessorTest {

	@Test
	public void test() {
		SMTPAccessor smtp = new SMTPAccessor("smtp.gmail.com", "tecnicasprueba01@gmail.com", "tecnicas01prueba");
		try {
			Message m = new Message("subject", "body");
			m.setFrom("tecnicasprueba01@gmail.com");
			m.setTo("aletorrado@gmail.com");
			smtp.sendMessage(m);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
