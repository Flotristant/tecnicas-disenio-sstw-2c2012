package model.tests;

import junit.framework.Assert;

import model.ActionAltaAlumno;

import org.junit.Test;

import persistence.mocks.StudentPersistenceMock;


public class ActionAltaAlumnoTestCase {

	@Test
	public void testShouldAltaAlumnoPassWhenCreateAnStudent() {
		StudentPersistenceMock studentPersistence = new StudentPersistenceMock();
		ActionAltaAlumno altaAlumno = new ActionAltaAlumno("7502","91227","francisco","francisco@soler", studentPersistence);
		
		altaAlumno.execute();
		
		Assert.assertEquals("7502", studentPersistence.getCodigoMateriaToSave());
		Assert.assertEquals("91227", studentPersistence.getPadronToSave());
		Assert.assertEquals("francisco", studentPersistence.getStudentToSave());
		Assert.assertEquals("francisco@soler", studentPersistence.getSenderToSave());
	}
}
