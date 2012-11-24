package model.tests;

import model.ActionValidateSender;
import model.Message;
import model.RuleAltaGrupo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import persistence.tests.mocks.MailPersistenceMock;
import persistence.tests.mocks.MateriaPersistenceMock;

public class ActionValidateSenderTestCase {

	private ActionValidateSender validatorEmail;
	private MailPersistenceMock mailPersistence;
	private MateriaPersistenceMock materiaPersistence;

	@Before
	public void setUp() throws Exception {
		this.mailPersistence = new MailPersistenceMock();
		this.validatorEmail = new ActionValidateSender(mailPersistence);
		this.materiaPersistence = new MateriaPersistenceMock();
	}
	
	@Test
	public void testShouldReturnTrueWhenValidateEmail() throws Exception {
		this.validatorEmail.initialize(new RuleAltaGrupo(materiaPersistence) , new Message("francisco", "", "subject", "body"));
		
		this.validatorEmail.execute();
		
		Assert.assertEquals("francisco", this.mailPersistence.getDirSender());
	}
	
	@Test (expected = Exception.class)
	public void testShouldFailIfMailIsNotInDatabase() throws Exception {
		this.validatorEmail.initialize(new RuleAltaGrupo(materiaPersistence) , new Message("caty", "", "subject", "body"));
		
		this.validatorEmail.execute();
	}
	
}
