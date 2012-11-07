package model.tests;


import java.util.HashMap;

import junit.framework.Assert;

import model.ActionSaveTp;
import model.Message;
import model.Rule;
import model.RuleAltaMateria;

import org.junit.Test;

import persistence.mocks.TpPersistenceMock;


public class ActionSaveTpTestCase {

	@Test
	public void testShouldPassWhenSaveTp() {
		Rule rule = new RuleAltaMateria("ruleAltaMateria");
		rule.setCodigoMateria("75.05");
		rule.setTpNumber("8");
		TpPersistenceMock tpPersistence = new TpPersistenceMock();
		Message message = new Message("sender", "", "", "");
		message.addAttachment("key1", "88".getBytes());
		message.addAttachment("key2", "89".getBytes());
		
		ActionSaveTp saveTp = new ActionSaveTp(tpPersistence);
		saveTp.initialize(rule, message);
		saveTp.execute();
		
		Assert.assertEquals("75.05", tpPersistence.getCodigoMateriaToSave());
		Assert.assertEquals("sender", tpPersistence.getSenderToSave());
		Assert.assertEquals(Integer.valueOf(8), tpPersistence.getTpNumberToSave());
		
		HashMap<String,byte[]> attachToSave = (HashMap<String, byte[]>) tpPersistence.getAttachmentsToSave();
		
		Assert.assertNotNull(attachToSave.get("key1"));
		Assert.assertNotNull(attachToSave.get("key2"));
		
		Assert.assertEquals(message.getAttachments().get("key1"), attachToSave.get("key1"));
		Assert.assertEquals(message.getAttachments().get("key2"), attachToSave.get("key2"));
		Assert.assertNull(attachToSave.get("key3"));
	}
}
