package modelTestCase;

import org.junit.Assert;
import org.junit.Test;

import persistenceMocks.MailPersistenceMock;

import Model.ActionValidateSender;

public class ActionValidarEmailTestCase {

	@Test
	public void testShouldReturnTrueWhenValidateEmail() {
		MailPersistenceMock mailPersistence = new MailPersistenceMock();
		ActionValidateSender validatorEmail = new ActionValidateSender("francisco", mailPersistence);
		
		Assert.assertTrue(validatorEmail.execute());
		
		Assert.assertEquals("francisco", mailPersistence.getDirSender());
	}
	
}
