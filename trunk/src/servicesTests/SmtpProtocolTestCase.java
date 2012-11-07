package servicesTests;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import services.SmtpProtocol;
import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;


public class SmtpProtocolTestCase {
	
	@Test
	public void testSmtpProtocolValid() {
		SmtpProtocol smtp = null;
		try {
			smtp = new SmtpProtocol("tecnicasprueba@gmail.com", "mailprueba", "2222", "smtp.live.com");
		}
		
		catch(InvalidPortFormatException e1) {
			fail("Formato de puerto invalido");
		} 
		catch (InvalidUserFormatException e) {
			fail("Formato de usuario invalido");
		}
		
		Assert.assertEquals("tecnicasprueba@gmail.com", smtp.getUser());
		Assert.assertEquals("mailprueba", smtp.getPass());
		Assert.assertEquals("2222", smtp.getPort());
		Assert.assertEquals("smtp.live.com", smtp.getHost());
	}
	
	@Test(expected = InvalidPortFormatException.class)
	public void testSmtpProtocolPortInvalid() throws InvalidPortFormatException {
		SmtpProtocol smtp = null;
		try {
			smtp = new SmtpProtocol("tecnicasprueba@gmail.com", "mailprueba", "2t22", "smtp.live.com");
		}

		catch (InvalidUserFormatException e) {
			fail("Formato de usuario invalido");
		}
		
	}
	
	@Test(expected = InvalidUserFormatException.class)
	public void testSmtpProtocolUserInvalid() throws InvalidUserFormatException {
		SmtpProtocol smtp = null;
		try {
			smtp = new SmtpProtocol("tecnicasprueba.gmail.com", "mailprueba", "2222", "smtp.live.com");
		}

		catch (InvalidPortFormatException e) {
			fail("Formato de puerto invalido");
		}
		
	}
}
