package model;

import persistence.IStudentPersistence;

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
	public boolean execute() {
		this.studentPersistence.saveStudent(codigo, padron, name, sender);
		return true;
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.padron = rule.getPadron();
		this.name = rule.getName();
		this.codigo = rule.getCodigoMateria();
		this.sender = this.message.getSender();
	}
	
}
