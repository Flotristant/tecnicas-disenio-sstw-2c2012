package model.tests;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import model.Email;
import services.Pop3Protocol;
import services.ReceiverProtocol;
import services.SenderProtocol;
import services.SmtpProtocol;


public class EmailTestCase {
	@Test
	public void testEmailValid() {
		
		Email email = null;
		SenderProtocol s = new SmtpProtocol("pepe@hotmail.com", "1234", "9999", "smpt.live.com");
		ReceiverProtocol r = new Pop3Protocol("pepe@hotmail.com", "1234", "9999", "pop3.live.com");
		try {
			email = new Email(s,r);
		} catch (Exception e) {
			fail ("Mail mal creado");
		}
		
		Assert.assertEquals("pepe@hotmail.com", email.getUser());
		Assert.assertEquals("1234", email.getPassword());
	}
	
	@Test  (expected = Exception.class)  
	public void testEmailWithProtocolsNotEquals() throws Exception {
		SenderProtocol s = new SmtpProtocol("pepe@hotmail.com", "1234", "9999", "smpt.live.com");
		ReceiverProtocol r = new Pop3Protocol("lol@aol.com", "password", "657", "pop3.live.com");
		Email email = new Email(s,r);
	}
	
	@Test
	public void testProcessEmail() {
		//TODO:
	}
}
