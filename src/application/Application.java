package application;

import static org.junit.Assert.fail;
import integrateTests.MailGeneratorMock;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.ClassAccount;
import model.Email;
import model.exceptions.InvalidAssociatedProtocolsException;
import persistence.exceptions.PersistenceException;

import controller.IProjectController;
import controller.ProjectController;

import application.DaemonThread.StatusChangedListener;
import application.DaemonThread.StatusChangedListener.status;

import services.Pop3Protocol;
import services.SmtpProtocol;
import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;
import ui.*;

public class Application {

	public static ClassAccount setUpClassAccount() throws NumberFormatException, PersistenceException {
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
		clas.persists();

		return clas;
	}
	
	public static ProjectController setUpProjectController() {
		Bootstrapper b = new Bootstrapper();
		b.run();
		return (ProjectController) b.getContainer().getComponent(IProjectController.class);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		ProjectController p = setUpProjectController();
		ClassAccount clas=null;
		try {
			clas = setUpClassAccount();
		} catch (NumberFormatException | PersistenceException e) {
			e.printStackTrace();
		}
//		while (true) {
			try {
				clas.processAccount(p);
			} catch (MessagingException | IOException | PersistenceException e) {
				e.printStackTrace();
			}
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		//}
	}
}
