package persistence;

import persistence.exceptions.PersistenceException;

public interface IStudentPersistence {

	void saveStudent(String codigoMateria, Integer padron, String name, String sender) throws PersistenceException;

	boolean validateStudentInCuatrimestre(String codigoMateria, Integer padron) throws PersistenceException;

	boolean validateStudentInGroup(String codigoMateria, Integer padron) throws PersistenceException;
}
