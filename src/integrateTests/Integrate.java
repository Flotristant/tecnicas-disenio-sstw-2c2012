package integrateTests;
import static org.junit.Assert.*;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Test;

import model.*;
import model.exceptions.InvalidAssociatedProtocolsException;
import services.*;
import application.Bootstrapper;
import controller.*;
import controller.factories.RuleControllerFactory;

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
		return clas;
	}
	
	
	public ProjectController setUpProjectController() {
		Bootstrapper b = new Bootstrapper();
		b.run();
		RuleControllerFactory r = new RuleControllerFactory(b.getContainer());
		IRuleController rulecontroller = r.create();
		return new ProjectController(r);
	}
	
	@Test
	public void testIntegral() {
		ClassAccount clase = this.setUpClassAccount();
		ProjectController pcontroller = this.setUpProjectController();
		try {
			clase.processAccount(pcontroller);
		} catch (MessagingException | IOException | PersistenceException e) {
			e.printStackTrace();
		}
	}
}
