package model.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Assert;

import model.ActionValidateAndSaveStudentInGroup;
import model.Message;
import model.Rule;
import model.RuleAltaGrupo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.tests.mocks.MateriaPersistenceMock;
import persistence.tests.mocks.StudentPersistenceMock;


public class ActionValidateAndSaveStudentInGroupTestCase {

	private StudentPersistenceMock studentPersistence;
	private Rule rule;
	private Message message;
	private HashMap<String, String> attach;
	private String pathIncoming;
	private MateriaPersistenceMock materiaPersistence;
	
	@Before
	public void setUp() throws Exception {
		this.materiaPersistence = new MateriaPersistenceMock();
		this.rule = new RuleAltaGrupo(materiaPersistence);
		this.rule.setCodigoMateria("7502");
		this.studentPersistence = new StudentPersistenceMock();
		this.message = new Message("", "", "", "");
		attach = new HashMap<String, String>();
		this.createAttachments("./testFiles/incoming/");
		this.pathIncoming = System.getProperty("user.dir");
		this.pathIncoming = this.pathIncoming+"/testFiles/incoming/";
		
	}

	@Test (expected = Exception.class)
	public void testShouldNotPassWhenValidateStudentInGroupWhithNoAttach() throws Exception {
		ActionValidateAndSaveStudentInGroup validateStudentInGroup = new ActionValidateAndSaveStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);

		validateStudentInGroup.execute();
	}
	
	@Test (expected = Exception.class)
	public void testShouldNotPassWhenMoreThanOneAttach() throws Exception {
		this.attach.put("padrones", "");
		this.attach.put("padrones2", "");
		
		this.message.addAttachments(this.attach);

		ActionValidateAndSaveStudentInGroup validateStudentInGroup = new ActionValidateAndSaveStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		validateStudentInGroup.execute();
	}
	
	@Test (expected = Exception.class)
	public void testShouldNotPassWhenAttachsContentIsNotPadrons() throws Exception {
		
		this.attach.clear();
		this.attach.put("attach1Invalid", this.pathIncoming);
		this.message.addAttachments(this.attach);
		
		ActionValidateAndSaveStudentInGroup validateStudentInGroup = new ActionValidateAndSaveStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		validateStudentInGroup.execute();
	}
	
	@Test (expected = Exception.class)
	public void testShouldNotPassWhenStudentBelongsInOtherGroup() throws Exception {
		
		this.attach.clear();
		this.attach.put("attach2Invalid", this.pathIncoming);
		this.message.addAttachments(this.attach);
		
		ActionValidateAndSaveStudentInGroup validateStudentInGroup = new ActionValidateAndSaveStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		validateStudentInGroup.execute();
	}
	
	@Test (expected = Exception.class)
	public void testShouldNotPassWhenStudentIsNotInCurrentSemester() throws Exception {
		
		this.attach.clear();
		this.attach.put("attach3Invalid", this.pathIncoming);
		this.message.addAttachments(this.attach);
		
		ActionValidateAndSaveStudentInGroup validateStudentInGroup = new ActionValidateAndSaveStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		
		validateStudentInGroup.execute();
	}
		
	@Test
	public void testShouldSaveStudentsCorrectlyInGroup() throws Exception {
		this.attach.clear();
		this.attach.put("attachValid", this.pathIncoming);
		this.message.addAttachments(this.attach);
		
		ActionValidateAndSaveStudentInGroup validateStudentInGroup = new ActionValidateAndSaveStudentInGroup(this.studentPersistence);
		validateStudentInGroup.initialize(this.rule, this.message);
		this.studentPersistence.clearPadrones();
		validateStudentInGroup.execute();
		ArrayList<Integer> padrones = new ArrayList<Integer>();
		padrones = this.studentPersistence.getPadrones();
		
		Assert.assertEquals(4,padrones.size());
		Assert.assertEquals(Integer.valueOf(91227), padrones.get(0));
		Assert.assertEquals(Integer.valueOf(90778), padrones.get(1));
		Assert.assertEquals(Integer.valueOf(91001), padrones.get(2));
		Assert.assertEquals(Integer.valueOf(91229), padrones.get(3));
	}
	
	
	private void createAttachments(String pathIncoming) throws IOException {
		File dir = new File (pathIncoming);
		if ( dir.mkdir()) {
			File directory = new File(pathIncoming + "attach1Invalid");
			directory.createNewFile();
			FileOutputStream out = new FileOutputStream(directory);
			out.write("h91227".getBytes());
			out.close();
			directory = new File(pathIncoming + "attach2Invalid");
			directory.createNewFile();
			out = new FileOutputStream(directory);
			out.write("91227 90778 90000 91111".getBytes());
			out.close();
			directory = new File(pathIncoming + "attach3Invalid");
			directory.createNewFile();
			out = new FileOutputStream(directory);
			out.write("91227 90778 90001 10000".getBytes());
			out.close();
			directory = new File(pathIncoming + "attachValid");
			directory.createNewFile();
			out = new FileOutputStream(directory);
			out.write("91227 90778 91001 91229".getBytes());
			out.close();
		}
	}
	
	
	private void deleteFichero(File dir) {
		File[] ficheros = dir.listFiles();
		for (int x = 0; x < ficheros.length; x++){
			if (ficheros[x].isDirectory())
				this.deleteFichero(ficheros[x]);
			ficheros[x].delete();
		}
		dir.delete();
	}
	
	@After
	public void setDown() throws Exception {
		File fichero = new File("./testFiles/incoming/");
		this.deleteFichero(fichero);
	}
	
}
