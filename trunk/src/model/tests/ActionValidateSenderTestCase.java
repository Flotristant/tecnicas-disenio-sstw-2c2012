package model.tests;

import model.ActionValidateSender;
import model.Message;
import model.RuleAltaGrupo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import persistence.mocks.MailPersistenceMock;

public class ActionValidateSenderTestCase {

	private ActionValidateSender validatorEmail;
	private MailPersistenceMock mailPersistence;

	@Before
	public void setUp() throws Exception {
		this.mailPersistence = new MailPersistenceMock();
		this.validatorEmail = new ActionValidateSender(mailPersistence);
	}
	
	@Test
	public void testShouldReturnTrueWhenValidateEmail() throws Exception {
		this.validatorEmail.initialize(new RuleAltaGrupo() , new Message("francisco", "", "subject", "body"));
		
		this.validatorEmail.execute();
		
		Assert.assertEquals("francisco", this.mailPersistence.getDirSender());
	}
	
	@Test (expected = Exception.class)
	public void testShouldFailIfMailIsNotInDatabase() throws Exception {
		this.validatorEmail.initialize(new RuleAltaGrupo() , new Message("caty", "", "subject", "body"));
		
		this.validatorEmail.execute();
	}
	
}
