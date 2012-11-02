package Model;

import java.util.HashSet;
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
		HashSet<String> keys = (HashSet<String>) this.attachment.keySet();
		if (keys.toArray().length > 1) return false;
		
		String body = this.attachment.get(keys.toArray()[0]).toString();
		
		String[] padrones = body.split(" |\n");
		//si hay algo no numérico rechazo todo el txt
		for (int i= 0; i < padrones.length; i++ )
			if (Integer.getInteger(padrones[i]) == null) return false;
		for (int i= 0; i < padrones.length; i++ ) {
			if (!this.studentPersistence.validateStudentInCuatrimestre(this.codigoMateria, padrones[i])) return false;
			if (!this.studentPersistence.validateStudentInGroup(this.codigoMateria, padrones[i])) return false;
		}
		return true;
	}
}
