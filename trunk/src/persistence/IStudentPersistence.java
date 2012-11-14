package persistence;

import persistence.exceptions.PersistenceException;

public interface IStudentPersistence {

	void saveStudent(String codigoMateria, String padron, String name, String sender) throws PersistenceException;

	boolean validateStudentInCuatrimestre(String codigoMateria, String padrones) throws PersistenceException;

	boolean validateStudentInGroup(String codigoMateria, String padron) throws PersistenceException;

}
