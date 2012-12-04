package integrateTests;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.junit.After;
import org.junit.Test;
import model.*;
import model.exceptions.InvalidAssociatedProtocolsException;
import services.*;
import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;

import persistence.exceptions.PersistenceException;
public class MailGenerator {
		
		
		public void sendMail() {
			 SmtpProtocol sender = null;
			 try {
				 sender = new SmtpProtocol("pruebatecnicas@hotmail.com", "Mailprueba01", "587", "smtp.live.com");
			 } catch (InvalidPortFormatException | InvalidUserFormatException e1) {
				fail("Sender armado invalido");
			 }
			 MailGeneratorMock mock = new MailGeneratorMock();
		 List<model.Message>list = mock.getAltaMateriaMessagesValid();
		 list.addAll(list);
//			 List<model.Message> list2 = mock.getAltaMateriaMessagesWithCodeInvalid();
//			 list.addAll(list2);
//			 List<model.Message> list3 = mock.getAMesageConsultaTicketClosed();
//			 list.addAll(list3);
//		     List<model.Message> list4 = mock.getSpamMessages();
//		     list.addAll(list4);
//			 List<model.Message> list5= mock.getAMessageAltaGrupoInvalid();
////			 list.addAll(list5);
////		 List<model.Message> list6 = mock.getAltaGruposMessagesValid("./testFiles/testFilesOutBox/", "padrones");
////		 list.addAll(list6);
////		 List<model.Message> list7 = mock.getEntregaTpMessagesValid("./testFiles/testFilesOutBox/", "modelo.xml");
////		 list.addAll(list7);
////		 List<model.Message> list8 = mock.getAMessageConsultaPublica();
////		 list.addAll(list8);
////			 List<model.Message> list9 = mock.getEntregaTpMessagesInvalid("./testFiles/testFilesOutBox/", "Semana02.pdf");
////			 list.addAll(list9);
				 try {
					sender.send(list);
				} catch (AddressException e1) {
					e1.printStackTrace();
				} catch (MessagingException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			 sender= null;
		}
		
		
		@Test
		public void testSendMails() throws MessagingException, IOException, PersistenceException {

			this.sendMail();


	}
	}


