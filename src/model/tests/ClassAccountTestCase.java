package model.tests;

import static org.junit.Assert.fail;
import java.util.List;

import junit.framework.Assert;
import model.ClassAccount;
import model.Email;
import model.exceptions.*;

import org.junit.Test;

import services.Pop3Protocol;
import services.ReceiverProtocol;
import services.SenderProtocol;
import services.SmtpProtocol;
import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;


public class ClassAccountTestCase {
	
	@Test
	public void testClassAccountValid() {
		ClassAccount cAcc;
			cAcc = new ClassAccount("Tecnicas de Diseño", "Materia de programacion", "75-10","grupo@yahoo.com");
			Assert.assertEquals("Tecnicas de Diseño", cAcc.getName());
			Assert.assertEquals("Materia de programacion", cAcc.getDescription());
			Assert.assertEquals("75-10", cAcc.getCode());
	}
	
	
	@Test
	public void testClassAccountEmails() {
		
		ClassAccount cAcc=null;
		cAcc = new ClassAccount("Tecnicas de Diseño", "Materia de programacion", "75-10","grupo@yahoo.com");
		Assert.assertEquals(cAcc.getEmails(), null);
		
		Email e,e2,e3;
		e=e2=e3=null;
		
		try {
			SenderProtocol s = new SmtpProtocol("pepe@hotmail.com", "1234", "9999", "smpt.live.com");
			ReceiverProtocol r = new Pop3Protocol("pepe@hotmail.com", "1234", "9999", "pop3.live.com","/home/gustavo/Escritorio");
			
			SenderProtocol s2 = new SmtpProtocol("abc@gmail.com", "2222", "9969", "smpt.live.com");
			ReceiverProtocol r2 = new Pop3Protocol("abc@gmail.com", "2222", "949", "pop3.live.com","/home/gustavo/Escritorio");
			
			SenderProtocol s3 = new SmtpProtocol("hsa2@yahoo.com", "sdsdsd", "4", "smpt.live.com");
			ReceiverProtocol r3 = new Pop3Protocol("hsa2@yahoo.com", "sdsdsd", "92", "pop3.live.com","/home/gustavo/Escritorio");
			e = new Email(s,r);
			e2 = new Email(s2 , r2);
			e3 = new Email( s3, r3);
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
		
		cAcc.addEmail(e);
		cAcc.addEmail(e2);
		cAcc.addEmail(e3);
		
		List<String> listaEmails = cAcc.getEmails();
		Assert.assertEquals(listaEmails.size(), 3);
		Assert.assertTrue(listaEmails.contains("pepe@hotmail.com"));
		Assert.assertTrue(listaEmails.contains("abc@gmail.com"));
		Assert.assertTrue(listaEmails.contains("hsa2@yahoo.com"));
		
		cAcc.removeEmail("abc@gmail.com");
		listaEmails = cAcc.getEmails();
		Assert.assertEquals(listaEmails.size(), 2);
		Assert.assertTrue(listaEmails.contains("pepe@hotmail.com"));
		Assert.assertFalse(listaEmails.contains("abc@gmail.com"));
		Assert.assertTrue(listaEmails.contains("hsa2@yahoo.com"));
				
	}

	
	
}
