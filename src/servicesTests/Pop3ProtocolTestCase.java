package servicesTests;

import static org.junit.Assert.fail;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import junit.framework.Assert;

import org.junit.Test;

import services.Pop3Protocol;
import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;


public class Pop3ProtocolTestCase {
	
	@Test
	public void testPop3ProcotolValidad() {
		Pop3Protocol pop = null;
		try {
			pop = new Pop3Protocol("tecnicasprueba@gmail.com", "mailprueba", "995", "pop.gmail.com","/home/gustavo/Escritorio");
		}
		
		catch(InvalidPortFormatException e1) {
			fail("Formato de puerto invalido");
		} 
		catch (InvalidUserFormatException e) {
			fail("Formato de usuario invalido");
		}
		
		Assert.assertEquals("tecnicasprueba@gmail.com", pop.getUser());
		Assert.assertEquals("mailprueba", pop.getPass());
		Assert.assertEquals("995", pop.getPort());
		Assert.assertEquals("pop.gmail.com", pop.getHost());
	}
	
	@Test(expected = InvalidPortFormatException.class)
	public void testPopProtocolPortInvalid() throws InvalidPortFormatException {
		try {
			@SuppressWarnings("unused")
			Pop3Protocol pop = new Pop3Protocol("tecnicasprueba@gmail.com", "mailprueba", "aab2", "pop.gmail.com","/home/gustavo/Escritorio");
		}

		catch (InvalidUserFormatException e) {
			fail("Formato de usuario invalido");
		}
		
	}
	
	@Test(expected = InvalidPortFormatException.class)
	public void testPopProtocolPortInvalid2() throws InvalidPortFormatException {
		try {
			 @SuppressWarnings("unused")
			Pop3Protocol pop = new Pop3Protocol("tecnicasprueba@gmail.com", "mailprueba", "-400", "pop.gmail.com","/home/gustavo/Escritorio");
		}

		catch (InvalidUserFormatException e) {
			fail("Formato de usuario invalido");
		}
		
	}
	
	@Test(expected = InvalidUserFormatException.class)
	public void testPopProtocolUserInvalid() throws InvalidUserFormatException {
		try {
			@SuppressWarnings("unused")
			Pop3Protocol pop = new Pop3Protocol("pepegmail.com", "mailprueba", "995", "pop.gmail.com","/home/gustavo/Escritorio");
		}

		catch (InvalidPortFormatException e) {
			fail("Formato de puerto invalido");
		}
		
	}
	
	@Test 
	public void testPopConectionValid() {
		 Pop3Protocol pop = null;
		 try {
			 pop = new Pop3Protocol("pruebatecnicas@gmail.com", "mailprueba", "995", "pop.gmail.com","/home/gustavo/Escritorio/");
		}
		 catch (Exception e) {
			 fail("Pop mal formado");
		 }
		 try {
			pop.receive();
		} 	
		 catch (AddressException e1) {
			 fail("Direccion invalida");
		 } catch (IOException e) {
			 fail("I/O incorrecto");
		} catch (MessagingException e) {
			fail("Conexion invalidad");
		}
	}
	
	@Test
	public void testReceiveMessageValid() {
		//TODO:
	}
	
	@Test(expected= MessagingException.class)
	public void testReceiveMessageInvalidHost() throws MessagingException {
	
		 Pop3Protocol pop = null;
		 try {
			 pop = new Pop3Protocol("tecnicasprueba@gmail.com", "mailprueba", "995", "HOST.ar","/home/gustavo/Escritorio/");
		}
		 catch (Exception e) {
			 fail("Pop mal formado");
		 }
		 try {
			pop.receive();
		} 	
		 catch (AddressException e1) {
			 fail("Direccion invalida");
		 } catch (IOException e) {
			 fail("I/O incorrecto");
		}
	}
	
	
	@Test(expected= MessagingException.class)
	public void testSendMessageInvalidIdentification() throws MessagingException {
		
		 Pop3Protocol pop = null;
		 try {
			 pop = new Pop3Protocol("tecnicasprueba@gmail.com", "CONTRASEniaFALSA", "995", "pop.gmail.com","/home/gustavo/Escritorio/");
		}
		 catch (Exception e) {
			 fail("Pop mal formado");
		 }
		 try {
			pop.receive();
		} 	
		 catch (AddressException e1) {
			 fail("Direccion invalida");
		 } catch (IOException e) {
			 fail("I/O incorrecto");
		}
	}
	
}
