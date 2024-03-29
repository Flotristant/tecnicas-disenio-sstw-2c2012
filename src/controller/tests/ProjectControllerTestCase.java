package controller.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import controller.tests.mocks.*;
import org.junit.Test;

import persistence.exceptions.PersistenceException;
import controller.ProjectController;
import controller.tests.mocks.RuleControllerFactoryMock;

public class ProjectControllerTestCase{

	private RuleControllerFactoryMock ruleControllerFactory;
	
	//Factory para probar solamente los mensajes de spam
	private RuleControllerFactoryTestSpamMock ruleControllerFactorySpamMock;

	@Before
	public void setUp() throws Exception {
		this.ruleControllerFactory = new RuleControllerFactoryMock();
		this.ruleControllerFactorySpamMock = new RuleControllerFactoryTestSpamMock();
		this.createAttachments("./testFiles/incoming/");
	}
	
	@Test
	public void testShouldCreateControllerCorrectlyWithNoExceptions() {
		this.createController();
	}
	
	private ProjectController createController(){
		return new ProjectController(this.ruleControllerFactory);
	}
	
	@Test
	public void testProcessMessageSpam() throws PersistenceException {
		
		//Test que ve que solo machee con la regla spam
		MessagesGeneratorMock mock = new MessagesGeneratorMock();
		List<model.Message> spamMessages = mock.getSpamMessages();
		ProjectController p = new ProjectController(this.ruleControllerFactorySpamMock);
		List<model.Message> anwser = p.processIncoming(spamMessages);
		Assert.assertEquals(anwser.size(), spamMessages.size());
		Iterator<model.Message> it = anwser.iterator();
		while(it.hasNext()) {
			model.Message m = it.next();
			Assert.assertEquals("Spam", m.getSubject());
		}
	}
	

	@Test
	public void testProcessMessageAltaMateria() throws PersistenceException {
		
		//alta de usuario
		MessagesGeneratorMock mock = new MessagesGeneratorMock();
		List<model.Message> altaMateriaMessages = mock.getAltaMateriaMessagesValid();
		ProjectController p = new ProjectController(this.ruleControllerFactory);
		List<model.Message> anwser = p.processIncoming(altaMateriaMessages);
		Assert.assertEquals(anwser, null);
		
		//alta de usuario con codigo incorrecto
		altaMateriaMessages = mock.getAltaMateriaMessagesWithCodeInvalid();
		anwser = p.processIncoming(altaMateriaMessages);
		Assert.assertEquals(anwser.size(),altaMateriaMessages.size());
		Iterator<model.Message> it = anwser.iterator();
		while (it.hasNext()) {
			model.Message m = it.next();
			Assert.assertEquals(m.getSubject(), "El código de la materia es incorrecto");
		}
		
		
	}
	
	@Test 
	public void testProcessMessageEntregaTp() throws PersistenceException {
		
		//Entrega de tp de un alumno que esta dado de alta
		String pathIncoming = System.getProperty("user.dir");
		pathIncoming = pathIncoming+"/testFiles/incoming/";
		MessagesGeneratorMock mock = new MessagesGeneratorMock();
		ProjectController p = new ProjectController(this.ruleControllerFactory);
		List<model.Message> entregaTpMessages = mock.getEntregaTpMessagesValid(pathIncoming,"TP1");
		List<model.Message>anwser = p.processIncoming(entregaTpMessages);
		Assert.assertEquals(anwser, null);
		
		//Entrega de tp de un alumno que no esta dado de alta
		pathIncoming = System.getProperty("user.dir");
		pathIncoming = pathIncoming+"/testFiles/incoming/";
		entregaTpMessages = mock.getEntregaTpMessagesInvalid(pathIncoming,"TP1");
		anwser = p.processIncoming(entregaTpMessages);
		Assert.assertEquals(anwser.size(), 1);
		model.Message me = anwser.get(0);
		Assert.assertEquals(me.getSubject(), "Sender doesn't belong to this course");
	}
	
	
	@Test
	public void testProcessMessageAltaGrupo() throws PersistenceException {
		
		//Mail invalido
		MessagesGeneratorMock mock = new MessagesGeneratorMock();
		List<model.Message> altaGrupoMessages = mock.getAltaGruposMessagesWithFakeMail();
		ProjectController p = new ProjectController(this.ruleControllerFactory);
		List<model.Message> anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(1,anwser.size());
		model.Message m = anwser.get(0);		
		Assert.assertEquals(m.getSubject(), "Sender doesn't belong to this course");
		
		//Mail sin attach
		altaGrupoMessages = mock.getAltaGruposMessagesWithNoAttach();
		anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(1,anwser.size());
		m = anwser.get(0);		
		Assert.assertEquals(m.getSubject(), "Message has no attachment");
		
		//Mail con datos y atacch validos
//		String pathIncoming = System.getProperty("user.dir");
		String pathIncoming = "./testFiles/incoming/";
		altaGrupoMessages = mock.getAltaGruposMessagesValid(pathIncoming,"attach1Valid");
		anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(anwser, null);
		
		//Mail con datos validos pero con un attach con padron incorrecto

		altaGrupoMessages = mock.getAltaGruposMessagesValid(pathIncoming,"attach2Invalid");
		anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(anwser.size(), 1);
		m = anwser.get(0);
		Assert.assertEquals(m.getSubject(), "Student doesn't belong to this course");
		
		//Mail con datos validos pero con el attach mal formado 

		altaGrupoMessages = mock.getAltaGruposMessagesValid(pathIncoming,"attach3Invalid");
		anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(anwser.size(), 1);
		m = anwser.get(0);
		Assert.assertEquals(m.getSubject(), "Attachment hasn't only numeric registers");

		//Mail con datos validos pero en el atach un padron ya perteneciente a otro grupo

		altaGrupoMessages = mock.getAltaGruposMessagesValid(pathIncoming,"attach4Invalid");
		anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(anwser.size(), 1);
		m = anwser.get(0);
		Assert.assertEquals(m.getSubject(), "Student already belong to another group");
		
		//Mail con mas de un attach

		altaGrupoMessages = mock.getAltaGruposMessagesWithMoreThanOneAttach(pathIncoming,"attach4Invalid");
		anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(anwser.size(), 1);
		m = anwser.get(0);
		Assert.assertEquals(m.getSubject(), "Message has too much attachments");
				
		}
		
	@Test
	public void testProcessMessageConsultaTema() {
		MessagesGeneratorMock mock = new MessagesGeneratorMock();
		List<model.Message> consultaTemaMessage = mock.getAMessageConsultaPublicaValid();
		ProjectController p = new ProjectController(this.ruleControllerFactory);
		List<model.Message> anwser = null;
		try {
			anwser = p.processIncoming(consultaTemaMessage);
			Assert.assertEquals(anwser.size(), 2);	
			Assert.assertEquals(anwser.get(0).getSubject(), "Se ha creado el ticket numero 1 del tema " + "Base de datos");
			Assert.assertEquals(anwser.get(0).getTo(), "from@hotmail.com");
			Assert.assertEquals(anwser.get(1).getSubject(), "Se ha creado el ticket numero 1 del tema " + "Base de datos");
			Assert.assertEquals(anwser.get(1).getTo(), "grupomateria@gmail.com");
		} catch (PersistenceException e) {
			fail();
		}
		
		consultaTemaMessage = mock.getAMessageConsultaPrivadaValid();
		try {
			anwser = p.processIncoming(consultaTemaMessage);
			Assert.assertEquals(anwser.size(), 1);
			model.Message m  = anwser.get(0);
			Assert.assertEquals(m.getSubject(), "Se ha creado el ticket numero 1 del tema " + "Punteros");
		} catch (PersistenceException e) {
			fail();
		}
	}
	
	@Test
	public void testProcessMessageConsultaTicket() {
		MessagesGeneratorMock mock = new MessagesGeneratorMock();
		List<model.Message> consultaTemaMessage = mock.getAMesageConsultaTicketValid();
		ProjectController p = new ProjectController(this.ruleControllerFactory);
		List<model.Message> anwser = null;
		try {
			anwser = p.processIncoming(consultaTemaMessage);
			Assert.assertEquals(anwser, null);	
		} catch (PersistenceException e) {
			fail();
		}
		
		consultaTemaMessage = mock.getAMesageConsultaTicketClosed();
		try {
			anwser = p.processIncoming(consultaTemaMessage);
			Assert.assertEquals(anwser.size(), 1);
			model.Message m  = anwser.get(0);
			Assert.assertEquals(m.getSubject(), "Ticket is closed");
		} catch (PersistenceException e) {
			fail();
		}
	}
	
	@Test
	public void testHandlerProjectMessage() {
		ProjectController p = new ProjectController(this.ruleControllerFactory);
		model.Message m = new model.Message("from@gmail.com","to@hotmail.com","[ENTREGA-TP1]","Blablabla");
		p.handleCreatedEvent(m, "Mail invalido");
		List<model.Message> answerMessages = p.getAnswerMessages();
		
		Assert.assertEquals(answerMessages.size(), 1);
		
		model.Message anwser = answerMessages.get(0);
		
		Assert.assertEquals(anwser.getSender(), m.getTo());
		Assert.assertEquals(anwser.getTo(), m.getSender());
		Assert.assertEquals(anwser.getSubject(), "Mail invalido");
		Assert.assertEquals(anwser.getBody(), m.getBody());
		
		Assert.assertEquals(anwser.isWithAttachments(), false);
	}
	
	
	private void createAttachments(String pathIncoming) throws IOException {
		File dir = new File (pathIncoming);
		if ( dir.mkdir()) {
			File directory = new File(pathIncoming + "attach1Valid");
			directory.createNewFile();
			FileOutputStream out = new FileOutputStream(directory);
			out.write("90100\n 90001\n 90200\n".getBytes());
			out.close();
			directory = new File(pathIncoming + "attach2Invalid");
			directory.createNewFile();
			out = new FileOutputStream(directory);
			out.write("90100\n 88888\n 90200\n".getBytes());
			out.close();
			directory = new File(pathIncoming + "attach3Invalid");
			directory.createNewFile();
			out = new FileOutputStream(directory);
			out.write("901d0\n 90001\n 90200\n".getBytes());
			out.close();
			directory = new File(pathIncoming + "attach4Invalid");
			directory.createNewFile();
			out = new FileOutputStream(directory);
			out.write("90001\n 90300\n".getBytes());
			out.close();
			directory = new File(pathIncoming + "TP1");
			directory.createNewFile();
			out = new FileOutputStream(directory);
			out.write("Este es un tp de Analisis de La informacion\n Casos de uso\n".getBytes());
			out.close();
		}
	}
	
	private void deleteFichero(File dir) {
		File[] ficheros = dir.listFiles();
		for (int x = 0; x < ficheros.length; x++){
			if (ficheros[x].isDirectory())
				this.deleteFichero(ficheros[x]);
			ficheros[x].delete();
		}
		dir.delete();
	}
	
	@After
	public void setDown() throws Exception {
		File fichero = new File("./testFiles/incoming/");
		this.deleteFichero(fichero);
	}
}
