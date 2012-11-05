package servicesTests;
import static org.junit.Assert.*;

import org.junit.Test;
import java.io.IOException;

import javax.mail.MessagingException;

import services.Pop3Protocol;
import services.SmtpProtocol;

import Model.ClassAccount;
import Model.Email;

//data mail de  para pruebas 
//user: pruebatecnicas@gmail.com pass: mailprueba
//user: pruebatecnicas@hotmail.com pass: Mailprueba01

public class IntegrateServicesTest {

	@Test
	public void integrateTest()  {
		 
		ClassAccount account = new ClassAccount("Tecnicas", 
				 "Materia de programacion", "70-02", "/home/gustavo/Escritorio");
		 SmtpProtocol s= new SmtpProtocol("pruebatecnicas@gmail.com", "mailprueba", "587", "smtp.gmail.com");
		 Pop3Protocol r = new Pop3Protocol("pruebatecnicas@gmail.com", "mailprueba", "995", "pop.gmail.com");
		 Email em = new Email("pruebatecnicas@gmail.com", "mailprueba", s, r);
		
		 account.addEmail(em);
		 
		 try {
			account.processAccount();
		} catch (MessagingException e) {
			e.printStackTrace();
			fail( "Error al manipular la clase messaging .");
		} 
		catch (IOException e) {
			e.printStackTrace();
			fail("Error failed or interrumpted i/o operation..");
			}
	}
}


