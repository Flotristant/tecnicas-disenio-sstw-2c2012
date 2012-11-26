package persistence;

import java.sql.ResultSet;

import persistence.exceptions.PersistenceException;

public class DBMateriaPersistence extends DBPersistence implements IMateriaPersistence {

	@Override
	public String getCodigoMateria(String email) throws PersistenceException {
		try {
			this.initializeDBMateria();
			ResultSet rs = this.statement.executeQuery(String.format("SELECT CodigoMateria FROM MATERIAS WHERE Email = %s", email));
			rs.next();
			String codigoMateria = rs.getString(1);
			rs.close();
			
			this.closeStatementAndConnection();
			return codigoMateria;
		} catch (Exception e) {
			throw new PersistenceException();
		}
	}

	@Override
	public void addMateria(int codigoMateria, String nombre, String desc,
			String email, String user, String pass, String pop3host, int pop3port, String smtphost, int smtpport) throws PersistenceException {
		try {
			this.initializeDBMateria();
			this.statement.executeUpdate(String.format("INSERT INTO MATERIAS (CodigoMateria, Descripcion, Nombre, Email, User, Pass, Pop3host, Pop3port, Smtphost, Smtpport) VALUES (%d, '%s');", codigoMateria, desc, nombre,
					email, user, pass, pop3host, pop3port, smtphost, smtpport));
						
			this.closeStatementAndConnection();
		} catch (Exception e) {
			throw new PersistenceException();
		}
		
	}

	public boolean validateMateria(String codigoMateria) throws PersistenceException {
		try {
			this.initializeDBMateria();
			int codigo = Integer.parseInt(codigoMateria);
			ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM MATERIAS WHERE CodigoMateria=%d;", codigo));
						
			rs.next();
			Integer count = Integer.valueOf(rs.getString(1));
			
			rs.close();
			this.closeStatementAndConnection();
			return count == 1;
		} catch (Exception e) {
			throw new PersistenceException();
		}
	}	
}
