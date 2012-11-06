package model.tests;

import java.util.ArrayList;

import junit.framework.Assert;
import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.Rule;

import org.junit.Test;

import persistence.mocks.StudentPersistenceMock;
import persistence.mocks.TpPersistenceMock;

public class RuleTestCase {
	
	@Test
	public void testShouldSaveCorrectlyPattern() {
		Rule rule = new Rule("pattern1");
		
		Assert.assertEquals("pattern1", rule.getPattern());
	}
	
	@Test
	public void testShouldSaveCorrectlyVariousActions() {
		ActionAltaAlumno action1 = new ActionAltaAlumno(new StudentPersistenceMock()); 
		ActionSaveTp action2 = new ActionSaveTp(new TpPersistenceMock());
		
		Rule rule = new Rule("pattern1");
		rule.addAction(action1);
		rule.addAction(action2);
		
		Assert.assertEquals(2, ((ArrayList<ActionRule>)rule.getActions()).size());
		
		Assert.assertEquals(action1, ((ArrayList<ActionRule>)rule.getActions()).get(0));
		Assert.assertEquals(action2, ((ArrayList<ActionRule>)rule.getActions()).get(1));
	}
}
