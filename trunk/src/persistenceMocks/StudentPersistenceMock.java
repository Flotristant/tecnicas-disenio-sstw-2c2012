package persistenceMocks;

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

}
