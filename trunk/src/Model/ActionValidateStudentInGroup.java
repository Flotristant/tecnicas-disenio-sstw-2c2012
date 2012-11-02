package Model;

import java.util.Map;
import persistence.IStudentPersistence;

public class ActionValidateStudentInGroup extends ActionRule {
	
	private IStudentPersistence studentPersistence;
	private String codigoMateria;
	private Map<String, byte[]> attachment;

	public ActionValidateStudentInGroup(String codigoMateria, Map<String, byte[]> attachment, IStudentPersistence studentPersistence){
		this.attachment = attachment;
		this.codigoMateria = codigoMateria;
		this.studentPersistence = studentPersistence;
	}

	@Override
	public boolean execute() {
		return validateStudentsInGroup();
	}
	
	private boolean validateStudentsInGroup() {
		if (this.attachment.keySet().size() != 1) return false;
		
		String body = new String(this.attachment.get(this.attachment.keySet().toArray()[0]));
		
		String[] padrones = body.split(" |\n");

		//si hay algo no numérico rechazo todo el txt
		for (int i = 0; i < padrones.length; i++)			
			try {
				Integer.valueOf(padrones[i]);
			}
			catch(NumberFormatException e) {
				return false;
			}
		for (int i = 0; i < padrones.length; i++) {
			if (!this.studentPersistence.validateStudentInCuatrimestre(this.codigoMateria, padrones[i])) return false;
			if (!this.studentPersistence.validateStudentInGroup(this.codigoMateria, padrones[i])) return false;
		}
		return true;
	}
}
