package model.tests;

import java.util.HashMap;

import junit.framework.Assert;
import model.ActionCreateTicket;
import model.Message;
import model.Rule;
import model.RuleConsultaTema;

import org.junit.Before;
import org.junit.Test;

import persistence.tests.mocks.MateriaPersistenceMock;
import persistence.tests.mocks.TicketPersistenceMock;

public class ActionCreateTicketTestCase {
	
	private TicketPersistenceMock ticketPersistence;
	private ActionCreateTicket createTicket;
	private MateriaPersistenceMock materiaPersistence;
	private Rule rule;

	@Before
	public void setUp() throws Exception {
		this.materiaPersistence = new MateriaPersistenceMock();
		this.rule = new RuleConsultaTema(this.materiaPersistence);
		this.ticketPersistence = new TicketPersistenceMock();
		this.createTicket = new ActionCreateTicket(this.ticketPersistence);
	}
	
	@Test
	public void testShouldPersistTicketCorrectly() throws Exception{
		this.rule.setPattern("ruleConsultaTema");
		this.rule.setCodigoMateria("7510");
		this.rule.setTypeOfQuery("PRIVADA");
		this.rule.setTema("tema1");
		
		HashMap<String, String> attachment = new HashMap<String, String>();
		attachment.put("key1", "88");
		attachment.put("key2", "89");
		
		Message message = new Message("sender", "to", "subject", "body");
		message.addAttachments(attachment);
		this.rule.setMessage(message);
		
		this.createTicket.initialize(this.rule, message);
		this.createTicket.execute();
		
		Assert.assertEquals("7510", this.ticketPersistence.getCodigoMateria());
		Assert.assertEquals(message, this.ticketPersistence.getMessage());
		Assert.assertEquals("tema1", this.ticketPersistence.getTema());
		Assert.assertEquals("PRIVADA", this.ticketPersistence.getTipo());
		
		
	
	}
}
