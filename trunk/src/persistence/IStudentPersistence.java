package persistence;

public interface IStudentPersistence {

	void saveStudent(String codigoMateria, String padron, String name, String sender);

	boolean validateStudentInCuatrimestre(String codigoMateria, String padrones);

	boolean validateStudentInGroup(String codigoMateria, String padron);

}
