package model.tests;

import java.util.ArrayList;

import junit.framework.Assert;
import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.Rule;
import model.RuleAltaGrupo;

import org.junit.Test;

import persistence.tests.mocks.MateriaPersistenceMock;
import persistence.tests.mocks.TpPersistenceMock;
import persistence.tests.mocks.StudentPersistenceMock;

public class RuleTestCase {
	private MateriaPersistenceMock materiaPersistence;
	
	@Test
	public void testShouldFailRuleWithoutPattern() throws Exception {
		this.materiaPersistence = new MateriaPersistenceMock();
		Rule rule = new RuleAltaGrupo(this.materiaPersistence);
		
		Assert.assertNull(rule.getPattern());
	}
	
	@Test
	public void testShouldSaveCorrectlyVariousActions() throws Exception {
		this.materiaPersistence = new MateriaPersistenceMock();
		ActionAltaAlumno action1 = new ActionAltaAlumno(new StudentPersistenceMock()); 
		ActionSaveTp action2 = new ActionSaveTp(new TpPersistenceMock());
		
		Rule rule = new RuleAltaGrupo(this.materiaPersistence);
		rule.addAction(action1);
		rule.addAction(action2);
		
		Assert.assertEquals(2, ((ArrayList<ActionRule>)rule.getActions()).size());
		
		Assert.assertEquals(action1, ((ArrayList<ActionRule>)rule.getActions()).get(0));
		Assert.assertEquals(action2, ((ArrayList<ActionRule>)rule.getActions()).get(1));
	}
}
