package servicesTests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import junit.framework.Assert;

import org.junit.Test;

import services.SmtpProtocol;
import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;
import model.Message;

//data mail de  para pruebas 
//user: pruebatecnicas@gmail.com pass: mailprueba host:smtp.gmail.com port:587
//user: pruebatecnicas@hotmail.com pass: Mailprueba01 host: port:
//


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
	
	@Test(expected = InvalidPortFormatException.class)
	public void testSmtpProtocolPortInvalid2() throws InvalidPortFormatException {
		SmtpProtocol smtp = null;
		try {
			smtp = new SmtpProtocol("tecnicasprueba@gmail.com", "mailprueba", "-222", "smtp.live.com");
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
	
	@Test
	public void testSendMessageValid() {
		 model.Message m = new model.Message("pruebatecnicas@gmail.com","pruebatecnicas@hotmail.com",
				 "subject","body");
		 List<model.Message> listm = new ArrayList<model.Message>();
		 listm.add(m);
		 SmtpProtocol smtp = null;
		 try {
			smtp = new SmtpProtocol("pruebatecnicas@gmail.com", "mailprueba", "587", "smtp.gmail.com");
		}
		 catch (Exception e) {
			 fail("Smtp mal formado");
		 }
		 try {
			smtp.debugOn();
			smtp.send(listm);
		} 	
		 catch (Exception e1) {
			 fail("Mail no enviado");
		 }
	}
	
	@Test(expected= MessagingException.class)
	public void testSendMessageInvalidHost() throws MessagingException {
		 model.Message m = new model.Message("pruebatecnicas@gmail.com","pruebatecnicas@hotmail.com",
				 "subject","body");
		 List<model.Message> listm = new ArrayList<model.Message>();
		 listm.add(m);
		 SmtpProtocol smtp = null;
		 try {
			smtp = new SmtpProtocol("pruebatecnicas@gmail.com", "mailprueba", "587", "smtp.pepemail.com");
		}
		 catch (Exception e) {
			 fail("Smtp mal formado");
		 }
		 try {
			smtp.debugOn();
			smtp.send(listm);
		} 	
		 catch (AddressException e1) {
			 fail("Direccion invalida");
		 }
	}
	
	
	@Test(expected= MessagingException.class)
	public void testSendMessageInvalidIdentification() throws MessagingException {
		 model.Message m = new model.Message("pruebatecnicas@gmail.com","pruebatecnicas@hotmail.com",
				 "subject","body");
		 List<model.Message> listm = new ArrayList<model.Message>();
		 listm.add(m);
		 SmtpProtocol smtp = null;
		 try {
			smtp = new SmtpProtocol("pruebatecnicas@gmail.com", "CONTRASENIAFALSA", "587", "smtp.gmail.com");
		}
		 catch (Exception e) {
			 fail("Smtp mal formado");
		 }
		 try {
			smtp.debugOn();
			smtp.send(listm);
		} 	
		 catch (AddressException e1) {
			 fail("Direccion invalida");
		 }
	}
	
}
