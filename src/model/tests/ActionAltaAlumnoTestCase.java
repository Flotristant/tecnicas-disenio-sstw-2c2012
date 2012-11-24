package model.tests;

import junit.framework.Assert;

import model.ActionAltaAlumno;
import model.Message;
import model.Rule;
import model.RuleAltaMateria;

import org.junit.Before;
import org.junit.Test;

import persistence.tests.mocks.MateriaPersistenceMock;
import persistence.tests.mocks.StudentPersistenceMock;


public class ActionAltaAlumnoTestCase {
	
	private ActionAltaAlumno altaAlumno;
	private StudentPersistenceMock studentPersistence;
	private MateriaPersistenceMock materiaPersistence;

	@Before
	public void setUp() throws Exception {
		this.studentPersistence = new StudentPersistenceMock();
		this.altaAlumno = new ActionAltaAlumno(studentPersistence);
		this.materiaPersistence = new MateriaPersistenceMock();
	}
	
	@Test
	public void testShouldAltaAlumnoPassWhenCreateAnStudent() throws Exception {
		Rule rule = new RuleAltaMateria(materiaPersistence);
		rule.setPattern("ruleAltaMateria");
		rule.setCodigoMateria("7510");
		rule.setCodigoMateriaInSubject("7510");
		rule.setPadron(91227);
		rule.setName("francisco");
		
		this.altaAlumno.initialize(rule, new Message("francisco@soler", "", "", ""));
		this.altaAlumno.execute();
		
		Assert.assertEquals("7510", this.studentPersistence.getCodigoMateriaToSave());
		Assert.assertEquals(Integer.valueOf(91227), this.studentPersistence.getPadronToSave());
		Assert.assertEquals("francisco", this.studentPersistence.getStudentToSave());
		Assert.assertEquals("francisco@soler", this.studentPersistence.getSenderToSave());
	}
	
	@Test(expected = Exception.class)
	public void testShouldFailWhenCodigoMateriaIsWrong() throws Exception{
		Rule rule = new RuleAltaMateria(materiaPersistence);
		rule.setPattern("ruleAltaMateria");
		rule.setCodigoMateria("7510");
		rule.setCodigoMateriaInSubject("7515");
		rule.setPadron(91227);
		rule.setName("francisco");
		
		this.altaAlumno.initialize(rule, new Message("francisco@soler", "", "", ""));
		this.altaAlumno.execute();
		
	}
}
