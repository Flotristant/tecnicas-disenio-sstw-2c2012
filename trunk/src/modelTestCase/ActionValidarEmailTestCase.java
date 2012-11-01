package modelTestCase;

import org.junit.Assert;
import org.junit.Test;

import persistenceMocks.MailPersistenceMock;

import Model.ActionValidarEmail;
import Model.Message;

public class ActionValidarEmailTestCase {

	@Test
	public void testShouldReturnTrueWhenValidateEmail() {
		Message message = new Message("francisco","pregunta","hola que tal?");
		MailPersistenceMock mailPersistence = new MailPersistenceMock();
		ActionValidarEmail validatorEmail = new ActionValidarEmail(message, mailPersistence);
		
		Assert.assertTrue(validatorEmail.execute());
		
		Assert.assertEquals("francisco", mailPersistence.getDirSender());
	}
	
}
