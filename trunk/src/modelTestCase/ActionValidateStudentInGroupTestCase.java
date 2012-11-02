package modelTestCase;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import persistenceMocks.StudentPersistenceMock;

import Model.ActionValidateStudentInGroup;

public class ActionValidateStudentInGroupTestCase {

	private StudentPersistenceMock studentPersistence;
	private HashMap<String, byte[]> attach;
	
	@Before
	public void setUp() throws Exception {
		studentPersistence = new StudentPersistenceMock();
		attach = new HashMap<String, byte[]>();
	}
	
	@Test
	public void testShouldNotPassWhenValidateStudentInGroupWhithNoAttach() {
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup("7502", this.attach, this.studentPersistence);

		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenMoreThanOneAttach() {
		this.attach.put("padrones", "".getBytes());
		this.attach.put("padrones2", "".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup("7502", this.attach, this.studentPersistence);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenAttachsContentIsNotPadrons() {
		this.attach.put("padrones", "h91227".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup("7502", this.attach, this.studentPersistence);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	//este test no pasa, el regex no esta funcionando bien
	@Test
	public void testShouldNotPassWhenStudentBelongsInOtherGroup() {
		this.attach.put("padrones", "91227 90778 90000 91111".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup("7502", this.attach, this.studentPersistence);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenStudentIsNotInCurrentSemester() {
		this.attach.put("padrones", "91227 90778 91001 10000".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup("7502", this.attach, this.studentPersistence);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
		
	@Test
	public void testShouldNotPassWhenStudentsAreDuplicatedInAttachment() {
		// TODO
	}
	
	@Test
	public void testShouldSaveStudentsCorrectlyInGroup() {
		this.attach.put("padrones", "91227 90778 91001 91229".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup("7502", this.attach, this.studentPersistence);
		
		Assert.assertTrue(validateStudentInGroup.execute());
	}
	
}
