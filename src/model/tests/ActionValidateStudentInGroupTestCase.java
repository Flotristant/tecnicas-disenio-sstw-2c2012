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
	private Rule rule;
	private Message message;
	private HashMap<String, String> attach;
	
	@Before
	public void setUp() throws Exception {
		this.rule = new RuleAltaGrupo();
		this.rule.setCodigoMateria("7502");
		this.studentPersistence = new StudentPersistenceMock();
		this.message = new Message("", "", "", "");
		attach = new HashMap<String, String>();
	}
	
	@Test
	public void testShouldNotPassWhenValidateStudentInGroupWhithNoAttach() {
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);

		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenMoreThanOneAttach() {
		this.attach.put("padrones", "");
		this.attach.put("padrones2", "");
		
		this.message.addAttachments(this.attach);

		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenAttachsContentIsNotPadrons() {
		this.attach.put("padrones", "h91227");
		this.message.addAttachments(this.attach);
		
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenStudentBelongsInOtherGroup() {
		this.attach.put("padrones", "91227 90778 90000 91111");
		this.message.addAttachments(this.attach);
		
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertFalse(validateStudentInGroup.execute());
	}
	
	@Test
	public void testShouldNotPassWhenStudentIsNotInCurrentSemester() {
		this.attach.put("padrones", "91227 90778 90001 10000");
		this.message.addAttachments(this.attach);
		
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
		this.attach.put("padrones", "91227 90778 91001 91229");
		this.message.addAttachments(this.attach);
		
		ActionValidateStudentInGroup validateStudentInGroup = new ActionValidateStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		Assert.assertTrue(validateStudentInGroup.execute());
	}
	
}
