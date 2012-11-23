package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import persistence.IStudentPersistence;
import sun.misc.IOUtils;

public class ActionValidateStudentInGroup extends ActionRule {
	
	private IStudentPersistence studentPersistence;
	private String codigoMateria;
	private Map<String, String> attachment;

	public ActionValidateStudentInGroup(IStudentPersistence studentPersistence) {
		this.studentPersistence = studentPersistence;
	}
	
	private String[] getStudentsId() throws IOException {
		Map.Entry<String, String> entry = this.attachment.entrySet().iterator().next();
		String file_path = entry.getValue() + entry.getKey();
		BufferedReader br = new BufferedReader(new FileReader(file_path));
		String everything ="";
		try {
		     StringBuilder sb = new StringBuilder();
		     String line = br.readLine();
		     while (line != null) {
		           sb.append(line);
		           line = br.readLine();
		     }
		     everything = sb.toString();
		    } 
		finally {
		    br.close();
		}
		String[] padrones = everything.split("\\s+");
		return 	padrones;
	}

	@Override
	public void execute() throws Exception {
		validateStudentsInGroup();
	}
	
	private void validateStudentsInGroup() throws Exception {
		if (this.attachment == null) throw new Exception("Message has no attachment");
		if (this.attachment.keySet().size() != 1) throw new Exception("Message has too much attachments");
		
		String[] padrones = this.getStudentsId();

		//si hay algo no numérico rechazo todo el txt
		for (int i = 0; i < padrones.length; i++)			
			try {
				Integer.valueOf(padrones[i]);
			}
			catch(NumberFormatException e) {
				throw new Exception("Attachment hasn't only numeric registers");
			}
		for (int i = 0; i < padrones.length; i++) {
			if (!this.studentPersistence.validateStudentInCuatrimestre(this.codigoMateria, Integer.valueOf(padrones[i]))) throw new Exception("Student doesn't belong to this course");
			if (!this.studentPersistence.validateStudentInGroup(this.codigoMateria, Integer.valueOf(padrones[i]))) throw new Exception("Student already belong to another group");
		}
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.attachment = this.message.getAttachments();
		this.codigoMateria = rule.getCodigoMateria();
	}
}
