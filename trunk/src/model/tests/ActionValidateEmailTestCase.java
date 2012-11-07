package model.tests;

import model.ActionValidateSender;
import model.Message;
import model.RuleAltaGrupo;

import org.junit.Assert;
import org.junit.Test;

import persistence.mocks.MailPersistenceMock;


public class ActionValidateEmailTestCase {

	@Test
	public void testShouldReturnTrueWhenValidateEmail() {
		MailPersistenceMock mailPersistence = new MailPersistenceMock();
		ActionValidateSender validatorEmail = new ActionValidateSender(mailPersistence);
		validatorEmail.initialize(new RuleAltaGrupo("rule1") , new Message("francisco", "", "subject", "body"));
		
		Assert.assertTrue(validatorEmail.execute());
		
		Assert.assertEquals("francisco", mailPersistence.getDirSender());
	}
	
}
