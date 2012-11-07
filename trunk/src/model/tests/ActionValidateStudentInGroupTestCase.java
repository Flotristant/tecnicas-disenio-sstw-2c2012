package model.tests;

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
	private Rule rule;
	private Message message;
	
	@Before
	public void setUp() throws Exception {
		this.rule = new RuleAltaGrupo();
		this.rule.setCodigoMateria("7502");
		this.studentPersistence = new StudentPersistenceMock();
		this.message = new Message("", "", "", "");
	}
	
	@Test
	public void testShouldNotPassWhenValidateStudentInGroupWhithNoAttach() {
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);

		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenMoreThanOneAttach() {
		this.message.addAttachment("padrones", "".getBytes());
		this.message.addAttachment("padrones2", "".getBytes());

		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenAttachsContentIsNotPadrons() {
		this.message.addAttachment("padrones", "h91227".getBytes());
		
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenStudentBelongsInOtherGroup() {
		this.message.addAttachment("padrones", "91227 90778 90000 91111".getBytes());
		
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenStudentIsNotInCurrentSemester() {
		this.message.addAttachment("padrones", "91227 90778 90001 10000".getBytes());
		
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
		this.message.addAttachment("padrones", "91227 90778 91001 91229".getBytes());
		
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertTrue(validateStudentInGroup.execute());
	}
	
}
