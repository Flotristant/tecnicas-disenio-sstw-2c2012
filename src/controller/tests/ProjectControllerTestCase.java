package controller.tests;

import java.util.List;

import junit.framework.Assert;

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
	
}
