package model.tests;

import java.util.HashMap;

import junit.framework.Assert;

import model.ActionSaveTp;
import model.Message;
import model.Rule;
import model.RuleAltaMateria;

import org.junit.Before;
import org.junit.Test;

import persistence.mocks.TpPersistenceMock;


public class ActionSaveTpTestCase {

	private Rule rule;
	private ActionSaveTp saveTp;
	private TpPersistenceMock tpPersistence;

	@Before
	public void setUp() throws Exception {
		this.tpPersistence = new TpPersistenceMock();
		this.saveTp = new ActionSaveTp(this.tpPersistence);
	}
	
	@Test
	public void testShouldPassWhenSaveTp() {
		this.rule = new RuleAltaMateria("ruleAltaMateria");
		this.rule.setCodigoMateria("75.05");
		this.rule.setTpNumber("8");
		
		Message message = new Message("sender", "to", "subject", "body");
		message.addAttachment("key1", "88".getBytes());
		message.addAttachment("key2", "89".getBytes());
		
		this.saveTp.initialize(this.rule, message);
		this.saveTp.execute();
		
		Assert.assertEquals("75.05", this.tpPersistence.getCodigoMateriaToSave());
		Assert.assertEquals("sender", this.tpPersistence.getSenderToSave());
		Assert.assertEquals(Integer.valueOf(8), this.tpPersistence.getTpNumberToSave());
		
		HashMap<String,byte[]> attachToSave = (HashMap<String, byte[]>) this.tpPersistence.getAttachmentsToSave();
		
		Assert.assertNotNull(attachToSave.get("key1"));
		Assert.assertNotNull(attachToSave.get("key2"));
		
		Assert.assertEquals(message.getAttachments().get("key1"), attachToSave.get("key1"));
		Assert.assertEquals(message.getAttachments().get("key2"), attachToSave.get("key2"));
		Assert.assertNull(attachToSave.get("key3"));
		
		Assert.assertEquals(Integer.valueOf(this.rule.getTpNumber()), this.tpPersistence.getTpNumberToSave());
		Assert.assertEquals(this.rule.getCodigoMateria(), this.tpPersistence.getCodigoMateriaToSave());
		Assert.assertEquals(message.getSender(), this.tpPersistence.getSenderToSave());
	}
}
