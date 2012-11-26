package integrateTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import model.*;
import model.exceptions.InvalidAssociatedProtocolsException;
import model.exceptions.InvalidPathDirectoryException;
import services.*;
import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;
import controller.*;
import controller.tests.mocks.RuleControllerFactoryMock;

import org.junit.Test;

import persistence.exceptions.PersistenceException;
import controller.tests.mocks.*;
public class ProjectControllerWithServices {

	
	@Test
	public void test1() {
		ClassAccount clas = null;
		// String pathIncoming = System.getProperty("user.dir");
		 String pathIncoming = "./testFiles/testFilesOutBox/";
		
		 try {
			clas = new ClassAccount("Tecnicsa de diseño", "Es una materia de programacion", "7510",pathIncoming);
		} catch (InvalidPathDirectoryException e1) {
			fail("Clase mal formada");
		}
		
		 SmtpProtocol smtp = null;
		try {
			 smtp = new SmtpProtocol("pruebatecnicas@gmail.com", "mailprueba", "587", "smtp.gmail.com");
		}
		catch (Exception e) {
				fail("Smtp mal formado");
		}
		
		Pop3Protocol pop = null;
		 try {
			 pop = new Pop3Protocol("pruebatecnicas@gmail.com", "mailprueba", "995", "pop.gmail.com",pathIncoming);
		}
		 catch (Exception e) {
			 fail("Pop mal formado");
		 }
		
		 Email e=null;
		try {
			e = new Email(smtp, pop);
		} catch (InvalidAssociatedProtocolsException e1) {
			fail("Email mal formado");
		}
		 clas.addEmail(e);
		 RuleControllerFactoryMock rfact= new RuleControllerFactoryMock();
		 ProjectController p = new ProjectController(rfact);

		 SmtpProtocol sender = null;
		 try {
			 sender = new SmtpProtocol("pruebatecnicas@hotmail.com", "Mailprueba01", "587", "smtp.live.com");
		 } catch (InvalidPortFormatException | InvalidUserFormatException e1) {
			fail("Sender armado invalido");
		 }

//
		 MessagesGeneratorMock mock = new MessagesGeneratorMock();
		 List<model.Message>list = mock.getAMessageAltaGrupoInvalid();
	

			 try {
 				sender.send(list);
			} catch (AddressException e1) {
				e1.printStackTrace();
			} catch (MessagingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		  

	 
		try {
			clas.processAccount(p);
		} catch (MessagingException e1) {
			e1.printStackTrace();
			fail("Error en en el manejo de mails");
		} catch (IOException e1) {
			fail("iMPUT/OUTPUT ERRROR");
		} catch (PersistenceException e1) {
			fail("error de persistencia");
		}
//		 
//		Pop3Protocol pop2 = null;
//		 try {
//			 pop2 = new Pop3Protocol("pruebatecnicas@hotmail.com", "Mailprueba01", "995", "pop3.live.com",pathIncoming);
//		}
//		 catch (Exception e2) {
//			 fail("Pop mal formado");
//		 }
//		
//		List<model.Message> respuesta = null;
//		try {
//			respuesta = pop2.receive();
//		} catch (MessagingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		Assert.assertEquals(1, respuesta.size());
//		model.Message m1= respuesta.get(0);
//		Assert.assertEquals(m1.getSubject(), "Sender doesn't belong to this course‏");
//		
	}
}
