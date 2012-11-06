package model;

import persistence.IStudentPersistence;

public class ActionAltaAlumno extends ActionRule {
	
	private IStudentPersistence studentPersistence;
	private String padron;
	private String name;
	private String codigo;
	private String sender;

	public ActionAltaAlumno(String codigoMateria,String padron, String name, String sender, IStudentPersistence studentPersistence) {
		this.studentPersistence = studentPersistence;
		this.padron = padron;
		this.name = name;
		this.codigo = codigoMateria;
		this.sender = sender;
	}
	
	public ActionAltaAlumno(IStudentPersistence studentPersistence) {
		this("", "", "", "", studentPersistence);
	}

	@Override
	public boolean execute() {
		this.studentPersistence.saveStudent(codigo, padron, name, sender);
		return true;
	}
	
}
