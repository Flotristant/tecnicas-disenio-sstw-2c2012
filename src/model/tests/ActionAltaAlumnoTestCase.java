package model.tests;

import junit.framework.Assert;

import model.ActionAltaAlumno;
import model.Message;
import model.Rule;
import model.RuleAltaMateria;

import org.junit.Test;

import persistence.mocks.StudentPersistenceMock;


public class ActionAltaAlumnoTestCase {

	@Test
	public void testShouldAltaAlumnoPassWhenCreateAnStudent() {
		Rule rule = new RuleAltaMateria("ruleAltaMateria");
		rule.setCodigoMateria("7502");
		rule.setPadron("91227");
		rule.setName("francisco");
		StudentPersistenceMock studentPersistence = new StudentPersistenceMock();
		ActionAltaAlumno altaAlumno = new ActionAltaAlumno(studentPersistence);
		altaAlumno.initialize(rule, new Message("francisco@soler", "", "", ""));
		altaAlumno.execute();
		
		Assert.assertEquals("7502", studentPersistence.getCodigoMateriaToSave());
		Assert.assertEquals("91227", studentPersistence.getPadronToSave());
		Assert.assertEquals("francisco", studentPersistence.getStudentToSave());
		Assert.assertEquals("francisco@soler", studentPersistence.getSenderToSave());
	}
}
