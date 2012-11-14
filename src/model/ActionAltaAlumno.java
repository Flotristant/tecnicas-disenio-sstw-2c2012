package model;

import persistence.IStudentPersistence;
import persistence.exceptions.PersistenceException;

public class ActionAltaAlumno extends ActionRule {
	
	private IStudentPersistence studentPersistence;
	private String padron;
	private String name;
	private String codigo;
	private String sender;

	public ActionAltaAlumno(IStudentPersistence studentPersistence) {
		this.studentPersistence = studentPersistence;
	}

	@Override
	public void execute() throws PersistenceException {
		this.studentPersistence.saveStudent(codigo, padron, name, sender);
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.padron = rule.getPadron();
		this.name = rule.getName();
		this.codigo = rule.getCodigoMateria();
		this.sender = this.message.getSender();
	}
	
}
