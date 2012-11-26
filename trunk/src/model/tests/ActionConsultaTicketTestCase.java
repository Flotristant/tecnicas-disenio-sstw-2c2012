package model.tests;

import java.util.HashMap;

import junit.framework.Assert;
import model.ActionConsultaTicket;
import model.Message;
import model.Rule;
import model.RuleConsultaTicket;

import org.junit.Before;
import org.junit.Test;

import persistence.tests.mocks.MateriaPersistenceMock;
import persistence.tests.mocks.TicketPersistenceMock;

public class ActionConsultaTicketTestCase {

	private TicketPersistenceMock ticketPersistence;
	private ActionConsultaTicket consultaTicket;
	private MateriaPersistenceMock materiaPersistence;
	private Rule rule;

	@Before
	public void setUp() throws Exception {
		this.rule = new RuleConsultaTicket(this.materiaPersistence);
		this.ticketPersistence = new TicketPersistenceMock();
		this.consultaTicket = new ActionConsultaTicket(this.ticketPersistence);
		this.materiaPersistence = new MateriaPersistenceMock();
	}
	
	@Test
	public void testShouldPersistTicketCorrectly() throws Exception{
		this.rule.setPattern("ruleConsultaTema");
		this.rule.setCodigoMateria("7510");
		this.rule.setTicketId("1");
		
		
		HashMap<String, String> attachment = new HashMap<String, String>();
		attachment.put("key1", "88");
		attachment.put("key2", "89");
		
		Message message = new Message("sender", "to", "subject", "body");
		message.addAttachments(attachment);
		this.rule.setMessage(message);
		
		this.consultaTicket.initialize(this.rule, message);
		this.consultaTicket.execute();
		
		Assert.assertEquals("7510", this.ticketPersistence.getCodigoMateria());

		Assert.assertEquals(Integer.valueOf(1), this.ticketPersistence.getIdTicket());

		Assert.assertEquals(message, this.ticketPersistence.getMessage());
		
		
	
	}
	
}
