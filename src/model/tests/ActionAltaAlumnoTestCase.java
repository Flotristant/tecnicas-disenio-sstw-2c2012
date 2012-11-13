package model.tests;

import junit.framework.Assert;

import model.ActionAltaAlumno;
import model.Message;
import model.Rule;
import model.RuleAltaMateria;

import org.junit.Before;
import org.junit.Test;

import persistence.tests.mocks.StudentPersistenceMock;


public class ActionAltaAlumnoTestCase {
	
	private ActionAltaAlumno altaAlumno;
	private StudentPersistenceMock studentPersistence;

	@Before
	public void setUp() throws Exception {
		this.studentPersistence = new StudentPersistenceMock();
		this.altaAlumno = new ActionAltaAlumno(studentPersistence);
	}
	
	@Test
	public void testShouldAltaAlumnoPassWhenCreateAnStudent() {
		Rule rule = new RuleAltaMateria();
		rule.setPattern("ruleAltaMateria");
		rule.setCodigoMateria("7502");
		rule.setPadron("91227");
		rule.setName("francisco");
		
		this.altaAlumno.initialize(rule, new Message("francisco@soler", "", "", ""));
		this.altaAlumno.execute();
		
		Assert.assertEquals("7502", this.studentPersistence.getCodigoMateriaToSave());
		Assert.assertEquals("91227", this.studentPersistence.getPadronToSave());
		Assert.assertEquals("francisco", this.studentPersistence.getStudentToSave());
		Assert.assertEquals("francisco@soler", this.studentPersistence.getSenderToSave());
	}
}
