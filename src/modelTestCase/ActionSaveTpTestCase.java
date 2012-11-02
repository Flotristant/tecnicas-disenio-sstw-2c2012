package modelTestCase;


import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;

import persistenceMocks.TpPersistenceMock;

import Model.ActionSaveTp;

public class ActionSaveTpTestCase {

	@Test
	public void testShouldPassWhenSaveTp() {
		TpPersistenceMock tpPersistence = new TpPersistenceMock();
		HashMap<String,byte[]> attach = new HashMap<String, byte[]>();
		attach.put("key1", "88".getBytes());
		attach.put("key2", "89".getBytes());
		
		ActionSaveTp saveTp = new ActionSaveTp("75.05", "sender", 8, attach, tpPersistence);
		saveTp.execute();
		
		Assert.assertEquals("75.05", tpPersistence.getCodigoMateriaToSave());
		Assert.assertEquals("sender", tpPersistence.getSenderToSave());
		Assert.assertEquals(Integer.valueOf(8), tpPersistence.getTpNumberToSave());
		
		HashMap<String,byte[]> attachToSave = (HashMap<String, byte[]>) tpPersistence.getAttachmentsToSave();
		
		Assert.assertNotNull(attachToSave.get("key1"));
		Assert.assertNotNull(attachToSave.get("key2"));
		Assert.assertEquals(attach.get("key1"), attachToSave.get("key1"));
		Assert.assertEquals(attach.get("key2"), attachToSave.get("key2"));
		Assert.assertNull(attachToSave.get("key3"));
	}
}
