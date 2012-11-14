package model.tests;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;


public class MessageTestCase {

	@Test
	public void testMessageValid() {
		
		model.Message m = new model.Message("from@gmail.com","to@hotmail.com","subject","body");
		Assert.assertEquals("from@gmail.com",m.getSender());
		Assert.assertEquals("to@hotmail.com", m.getTo());
		Assert.assertEquals("subject",m.getSubject());
		Assert.assertEquals("body",m.getBody());
				
	}
	
	@Test
	public void testSplitDirs() {
		
		model.Message m2 = new model.Message("from@gmail.com","to@hotmail.com","subject","body");
		
		List<String >dirTo = null;
		
		m2.addBCC("bcc@gmail.com,bcc@yahoo.com,bcc@hotmail.com");
		dirTo = m2.getToBCC();
		Assert.assertEquals(3,dirTo.size());
		Assert.assertTrue(dirTo.contains("bcc@gmail.com"));
		Assert.assertTrue(dirTo.contains("bcc@yahoo.com"));
		Assert.assertTrue(dirTo.contains("bcc@hotmail.com"));
		
		m2.addCC("cc@gmail.com, cc@yahoo.com");
		dirTo = m2.getToCC();
		Assert.assertEquals(2,dirTo.size());
		Assert.assertTrue(dirTo.contains("cc@gmail.com"));
		Assert.assertTrue(dirTo.contains("cc@yahoo.com"));
	}
}
