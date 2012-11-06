package persistence.mocks;

import persistence.IStudentPersistence;

public class StudentPersistenceMock implements IStudentPersistence {

	
	
	private String codigoMateria;
	private String padron;
	private String name;
	private String sender;

	public String getStudentToSave() {
		return this.name;
	}

	public String getCodigoMateriaToSave() {
		return this.codigoMateria;
	}

	public String getPadronToSave() {
		return this.padron;
	}

	public String getSenderToSave() {
		return this.sender;
	}

	@Override
	public void saveStudent(String codigoMateria, String padron, String name,
			String sender) {
		this.codigoMateria = codigoMateria;
		this.padron = padron;
		this.name = name;
		this.sender = sender;
	}

	@Override
	public boolean validateStudentInCuatrimestre(String codigoMateria,
			String padron) {
		this.padron = padron;
		this.codigoMateria = codigoMateria;
		
		if (padron.equals("10000"))
			return false;
		return true;
	}

	@Override
	public boolean validateStudentInGroup(String codigoMateria, String padron) {
		this.codigoMateria = codigoMateria;
		this.padron = padron;
		if (padron.equals("90000"))
			return false;
		return true;
	}

}
