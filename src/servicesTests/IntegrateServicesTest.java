package servicesTests;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import services.Pop3Protocol;
import services.SmtpProtocol;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;



//data mail de  para pruebas 
//user: pruebatecnicas@gmail.com pass: mailprueba
//user: pruebatecnicas@hotmail.com pass: Mailprueba01

public class IntegrateServicesTest {

	/* Envio un mail con adjuntos a un mail y luego abro el mismo
	 * verificando que lo que se envio es igual a lo que se recibe
	 */
	
	private void vaciarDirectorioIncoming(String pathIncoming) {
		File directory = new File(pathIncoming);
		File[] files = directory.listFiles();
		for(File file : files) {
			file.delete();
		}
	}
	
	@Test
	public void integrateTest()  {
		
		 model.Message m = new model.Message("pruebatecnicas@gmail.com","pruebatecnicas@hotmail.com",
				 "subject","body");
		 
		 String pathOutcoming = System.getProperty("user.dir");
		 pathOutcoming = pathOutcoming+"/testFiles/testFilesOutBox/";
		 HashMap<String, String> hm=  new HashMap<String, String>();
		 hm.put("Semana02.pdf",pathOutcoming);
		 hm.put("modelo.xml", pathOutcoming);
		 m.addAttachments(hm);
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
		//	smtp.debugOn();
			smtp.send(listm);
		} 	
		 catch (AddressException e1) {
			 fail("Error en la traduccion de la internet address");			 
		 } catch (MessagingException e) {
			 fail ("Error con la conexion con el host");
		} catch (IOException e) {
			fail ("Error con la manipulacion de los adjuntos");
		}
		
		 Pop3Protocol pop = null;
		 String pathIncoming = System.getProperty("user.dir");
		 pathIncoming = pathIncoming +"/testFiles/testFilesInBox/";
		 this.vaciarDirectorioIncoming(pathIncoming);
		 try {
			 pop = new Pop3Protocol("pruebatecnicas@hotmail.com", "Mailprueba01", "995", "pop3.live.com",pathIncoming);
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
	
		File adj1 = new File(pathIncoming+"Semana02.pdf");
		Assert.assertTrue(adj1.isFile());
		File adj2 = new File(pathIncoming+"modelo.xml");
		Assert.assertTrue(adj2.isFile());
		
	}
}


