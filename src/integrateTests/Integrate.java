package integrateTests;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import model.*;
import model.exceptions.InvalidAssociatedProtocolsException;
import services.*;
import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;
import application.Bootstrapper;
import controller.*;
import controller.factories.IRuleControllerFactory;
import controller.factories.RuleControllerFactory;
import controller.tests.mocks.MessagesGeneratorMock;

import persistence.exceptions.PersistenceException;

public class Integrate {
	
	public ClassAccount setUpClassAccount() {
		ClassAccount clas = null;
		String pathIncoming = "./testFiles/testFilesOutBox/";
			clas = new ClassAccount("Tecnicas de diseño", "Es una materia de programacion", "7510", "grupo@yahoo.com");

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
		 try {
			clas.persists();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (PersistenceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return clas;
	}
	
	public void sendMail() {
		 SmtpProtocol sender = null;
		 try {
			 sender = new SmtpProtocol("pruebatecnicas@hotmail.com", "Mailprueba01", "587", "smtp.live.com");
		 } catch (InvalidPortFormatException | InvalidUserFormatException e1) {
			fail("Sender armado invalido");
		 }
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
		  
	}
	
	public ProjectController setUpProjectController() {
		Bootstrapper b = new Bootstrapper();
		b.run();
		return (ProjectController) b.getContainer().getComponent(IProjectController.class);
	}
	
	@Test
	public void testIntegral() {
		ClassAccount clase = this.setUpClassAccount();
		ProjectController pcontroller = this.setUpProjectController();
//	this.sendMail();
		try {
			clase.processAccount(pcontroller);
		} catch (MessagingException | IOException | PersistenceException e) {
			e.printStackTrace();
		}
	}
	
	
	@After
	public void setDown() throws Exception {
		File fichero = new File("Materias.db");
		fichero.delete();
	}
}
