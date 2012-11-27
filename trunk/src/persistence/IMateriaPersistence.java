package persistence;

import persistence.exceptions.PersistenceException;

public interface IMateriaPersistence {

	String getCodigoMateria(String email) throws PersistenceException;

	void addMateria(int codigoMateria, String nombre, String desc,
			String email, String user, String pass, String pop3host, int pop3port, String smtphost, int smtpport) throws PersistenceException;

	String getGroupMailMateria(String codigoMateria) throws PersistenceException;

	void addGroupMailMateria(String codigoMateria, String mail)
			throws PersistenceException;
}
