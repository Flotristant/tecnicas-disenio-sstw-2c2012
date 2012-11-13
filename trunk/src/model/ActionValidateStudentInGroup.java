package model;

import java.util.Map;
import persistence.IStudentPersistence;

public class ActionValidateStudentInGroup extends ActionRule {
	
	private IStudentPersistence studentPersistence;
	private String codigoMateria;
	private Map<String, String> attachment;

	public ActionValidateStudentInGroup(IStudentPersistence studentPersistence) {
		this.studentPersistence = studentPersistence;
	}

	@Override
	public void execute() throws Exception {
		validateStudentsInGroup();
	}
	
	private void validateStudentsInGroup() throws Exception {
		if (this.attachment == null) throw new Exception("Message has no attachment");
		if (this.attachment.keySet().size() != 1) throw new Exception("Message has too much attachments");
		
		String body = new String(this.attachment.get(this.attachment.keySet().toArray()[0]));
		
		String[] padrones = body.split(" |\n");

		//si hay algo no numérico rechazo todo el txt
		for (int i = 0; i < padrones.length; i++)			
			try {
				Integer.valueOf(padrones[i]);
			}
			catch(NumberFormatException e) {
				throw new Exception("Attachment hasn't only numeric registers");
			}
		for (int i = 0; i < padrones.length; i++) {
			if (!this.studentPersistence.validateStudentInCuatrimestre(this.codigoMateria, padrones[i])) throw new Exception("Student doesn't belong to this course");
			if (!this.studentPersistence.validateStudentInGroup(this.codigoMateria, padrones[i])) throw new Exception("Student already belong to another group");
		}
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.attachment = this.message.getAttachments();
		this.codigoMateria = rule.getCodigoMateria();
	}
}
