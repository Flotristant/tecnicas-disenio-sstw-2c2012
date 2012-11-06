package modelTestCase;

import model.ActionValidateSender;

import org.junit.Assert;
import org.junit.Test;

import persistenceMocks.MailPersistenceMock;


public class ActionValidarEmailTestCase {

	@Test
	public void testShouldReturnTrueWhenValidateEmail() {
		MailPersistenceMock mailPersistence = new MailPersistenceMock();
		ActionValidateSender validatorEmail = new ActionValidateSender("francisco", mailPersistence);
		
		Assert.assertTrue(validatorEmail.execute());
		
		Assert.assertEquals("francisco", mailPersistence.getDirSender());
	}
	
}
