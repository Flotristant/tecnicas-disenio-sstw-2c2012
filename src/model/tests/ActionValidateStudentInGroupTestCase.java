package model.tests;

import java.util.HashMap;

import junit.framework.Assert;

import model.ActionValidateStudentInGroup;
import model.Message;
import model.Rule;
import model.RuleAltaGrupo;

import org.junit.Before;
import org.junit.Test;

import persistence.mocks.StudentPersistenceMock;


public class ActionValidateStudentInGroupTestCase {

	private StudentPersistenceMock studentPersistence;
	private HashMap<String, byte[]> attach;
	private Rule rule;
	private Message message;
	
	@Before
	public void setUp() throws Exception {
		this.rule = new RuleAltaGrupo("rule1");
		this.rule.setCodigoMateria("7502");
		this.studentPersistence = new StudentPersistenceMock();
		this.attach = new HashMap<String, byte[]>();
		this.message = new Message("", "", "", this.attach);
	}
	
	@Test
	public void testShouldNotPassWhenValidateStudentInGroupWhithNoAttach() {
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);

		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenMoreThanOneAttach() {
		this.attach.put("padrones", "".getBytes());
		this.attach.put("padrones2", "".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenAttachsContentIsNotPadrons() {
		this.attach.put("padrones", "h91227".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	//este test no pasa, el regex no esta funcionando bien
	@Test
	public void testShouldNotPassWhenStudentBelongsInOtherGroup() {
		this.attach.put("padrones", "91227 90778 90000 91111".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenStudentIsNotInCurrentSemester() {
		this.attach.put("padrones", "91227 90778 91001 10000".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
		
	@Test
	public void testShouldNotPassWhenStudentsAreDuplicatedInAttachment() {
		// TODO
	}
	
	@Test
	public void testShouldSaveStudentsCorrectlyInGroup() {
		this.attach.put("padrones", "91227 90778 91001 91229".getBytes());
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertTrue(validateStudentInGroup.execute());
	}
	
}
