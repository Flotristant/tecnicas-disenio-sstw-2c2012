package model.tests;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

import model.Email;
import model.exceptions.*;
import services.Pop3Protocol;
import services.ReceiverProtocol;
import services.SenderProtocol;
import services.SmtpProtocol;
import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;


public class EmailTestCase {
	@Test
	public void testEmailValid() {
		
		Email email = null;
		SenderProtocol s = null;
		ReceiverProtocol r = null;
		try {
			 s = new SmtpProtocol("pepe@hotmail.com", "1234", "9999", "smpt.live.com");
			 r = new Pop3Protocol("pepe@hotmail.com", "1234", "9999", "pop3.live.com", "/home/gustavo/Escritorio");
			email = new Email(s,r);
		}
		
		catch (InvalidAssociatedProtocolsException exce1) {
			fail("Mails mal Creados");
		}
		
		catch (InvalidUserFormatException exce2) {
			fail ("Protocolos con usuario en formato incorrecto");
		}
		
		catch (InvalidPortFormatException exce3) {
			fail ("Protocolos con puertos en formato incorrecto");
		}
		
		Assert.assertEquals("pepe@hotmail.com", email.getUser());
		Assert.assertEquals("1234", email.getPassword());
	}
	
	@Test  (expected = InvalidAssociatedProtocolsException.class)  
	public void testEmailWithProtocolsNotEquals() throws InvalidAssociatedProtocolsException {
		try {
			SenderProtocol s = new SmtpProtocol("pepe@hotmail.com", "1234", "9999", "smpt.live.com");
			ReceiverProtocol r = new Pop3Protocol("lol@aol.com", "password", "657", "pop3.live.com","/home/gustavo/Escritorio");
			@SuppressWarnings("unused")
			Email email = new Email(s,r);
		}
		catch (InvalidUserFormatException exce2) {
			fail ("Protocolos con usuario en formato incorrecto");
		}
		
		catch (InvalidPortFormatException exce3) {
			fail ("Protocolos con puertos en formato incorrecto");
		}
		
	}
	
	@Test
	public void testProcessEmail() {
		//TODO:
	}
}
