package model.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.Assert;

import model.ActionSpam;
import model.Message;
import model.RuleSpam;

import org.junit.Test;

import persistence.tests.mocks.MateriaPersistenceMock;

public class ActionSpamTestCase {
	
	private ActionSpam actionSpam;
	private RuleSpam rule;
	private MateriaPersistenceMock materiaPersistence;
	
	@Test
	public void testShouldDeleteFilesCorrectly() throws Exception {
		this.actionSpam = new ActionSpam();
		this.materiaPersistence = new MateriaPersistenceMock();
		this.rule = new RuleSpam(materiaPersistence);
		HashMap<String, String> attachments = new  HashMap<String, String>();
		attachments.put("attach1", "./testFiles/incoming/");
		attachments.put("attach2", "./testFiles/incoming/");
		this.createAttachments("./testFiles/incoming/");
		
		File fichero = new File("./testFiles/incoming/attach1");
		Assert.assertTrue(fichero.exists());
		fichero = new File("./testFiles/incoming/attach2");
		Assert.assertTrue(fichero.exists());
		
		Message message = new Message("from", "to", "subject", "body");
		message.addAttachments(attachments);
		this.actionSpam.initialize(rule, message );
		this.actionSpam.execute();
		
		fichero = new File("./testFiles/incoming/attach1");
		Assert.assertFalse(fichero.exists());
		fichero = new File("./testFiles/incoming/attach2");
		Assert.assertFalse(fichero.exists());
	}

	private void createAttachments(String pathIncoming) throws IOException {
		File dir = new File (pathIncoming);
		dir.mkdir();
		File directory = new File(pathIncoming + "attach1");
		directory.createNewFile();
		FileOutputStream out = new FileOutputStream(directory);
		out.write("attach1".getBytes());
		out.close();
		directory = new File(pathIncoming + "attach2");
		directory.createNewFile();
		out = new FileOutputStream(directory);
		out.write("attach2".getBytes());
		out.close();
	}
	
}
