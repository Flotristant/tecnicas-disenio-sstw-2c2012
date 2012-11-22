package controller.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import controller.tests.mocks.*;
import org.junit.Test;

import controller.ProjectController;
import controller.tests.mocks.RuleControllerFactoryMock;

@SuppressWarnings("unused")
public class ProjectControllerTestCase{

	private RuleControllerFactoryMock ruleControllerFactory;

	@Before
	public void setUp() throws Exception {
		this.ruleControllerFactory = new RuleControllerFactoryMock();
		this.createAttachments("./testFiles/incoming/");
	}
	
	@Test
	public void testShouldCreateControllerCorrectlyWithNoExceptions() {
		ProjectController p = this.createController();
	}
	
	private ProjectController createController(){
		return new ProjectController(this.ruleControllerFactory);
	}
	
	@Test
	public void testProcessMessageSpam() {
//		MessagesGeneratorMock mock = new MessagesGeneratorMock();
//		List<model.Message> spamMessages = mock.getSpamMessages();
//		ProjectController p = new ProjectController(this.ruleControllerFactory);
//		List<model.Message> anwser = p.processIncoming(spamMessages);
//		Assert.assertEquals(anwser.size(), spamMessages.size());
	}
	
//	@Test
//	public void testProcessMessageAltaGrupos() {
//		MessagesGeneratorMock mock = new MessagesGeneratorMock();
//		List<model.Message> spamMessages = mock.getAltaGruposMessages();
//		ProjectController p = new ProjectController(this.ruleControllerFactory);
//		List<model.Message> anwser = p.processIncoming(spamMessages);
//		Assert.assertEquals(anwser.size(), spamMessages.size());
//	}
	@Test
	public void testProcessMessageAltaMateria() {
//		MessagesGeneratorMock mock = new MessagesGeneratorMock();
//		List<model.Message> altaMateriaMessages = mock.getAltaMateriaMessages();
//		ProjectController p = new ProjectController(this.ruleControllerFactory);
//		List<model.Message> anwser = p.processIncoming(altaMateriaMessages);
//		Assert.assertEquals(anwser.size(), altaMateriaMessages.size());
	}
	
	@Test
	public void testProcessMessageAltaGrupo() {
		
		MessagesGeneratorMock mock = new MessagesGeneratorMock();
		List<model.Message> altaGrupoMessages = mock.getAltaGruposMessagesWithFakeMail();
		ProjectController p = new ProjectController(this.ruleControllerFactory);
		List<model.Message> anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(1,anwser.size());
		model.Message m = anwser.get(0);		
		Assert.assertEquals(m.getSubject(), "Sender doesn't belong to this course");
		
		altaGrupoMessages = mock.getAltaGruposMessagesWithNoAttach();
		anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(1,anwser.size());
		m = anwser.get(0);		
		Assert.assertEquals(m.getSubject(), "Message has no attachment");
		
		altaGrupoMessages = mock.getAltaGruposMessagesValidos("./testFiles/incoming/","attach1");
		anwser = p.processIncoming(altaGrupoMessages);
		Assert.assertEquals(0, anwser.size());
		
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
		dir.mkdir();
		File directory = new File(pathIncoming + "attach1");
		directory.createNewFile();
		FileOutputStream out = new FileOutputStream(directory);
		out.write("90100".getBytes());
		out.close();
//		directory = new File(pathIncoming + "attach2");
//		directory.createNewFile();
//		out = new FileOutputStream(directory);
//		out.write("attach2".getBytes());
//		out.close();
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
