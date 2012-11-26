package model;

import model.listeners.IResponseMailEventListener;
import persistence.IStudentPersistence;

public class ActionAltaAlumno extends ActionRule {
	
	private IStudentPersistence studentPersistence;
	private Integer padron;
	private String name;
	private String codigo;
	private String codigoInSubject;
	private String sender;

	public ActionAltaAlumno(IStudentPersistence studentPersistence) {
		this.studentPersistence = studentPersistence;
	}

	@Override
	public void execute() throws Exception {
		if(codigo.equals(codigoInSubject))
			this.studentPersistence.saveStudent(codigo, padron, name, sender);
		else
			throw new Exception("El código de la materia es incorrecto");
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.padron = rule.getPadron();
		this.name = rule.getName();
		this.codigo = rule.getCodigoMateria();
		this.sender = this.message.getSender();
		this.codigoInSubject = rule.getCodigoMateriaInSubject();
	}

	@Override
	protected void addSubscriber(IResponseMailEventListener listener) {
	}
	
}
