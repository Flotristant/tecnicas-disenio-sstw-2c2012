package persistence;

import java.sql.DriverManager;
import java.sql.ResultSet;

import persistence.exceptions.PersistenceException;

public class DBMateriaPersistence extends DBPersistence implements IMateriaPersistence {

	@Override
	public String getCodigoMateria(String email) throws PersistenceException {
		try {
			this.initializeDBMateria();
			ResultSet rs = this.statement.executeQuery(String.format("SELECT CodigoMateria FROM MATERIAS WHERE Email = '%s'", email));
			
			rs.next();
			String codigoMateria = rs.getString(1);
			rs.close();
			
			this.closeStatementAndConnection();
			return codigoMateria;
		} catch (Exception e) {
			System.out.print("Clase ya existente");
		//	e.printStackTrace();
			throw new PersistenceException();
		}
	}

	@Override
	public void addMateria(int codigoMateria, String nombre, String desc,
			String email, String user, String pass, String pop3host, int pop3port, String smtphost, int smtpport) throws PersistenceException {
		try {
			this.initializeDBMateria();
			this.statement.executeUpdate(String.format("INSERT OR REPLACE INTO MATERIAS (CodigoMateria, Descripcion, Nombre, Email, Pass, Pop3host, Pop3port, Smtphost, Smtpport) VALUES (%d, '%s', '%s', '%s', '%s', '%s', %d, '%s', %d);", codigoMateria, desc, nombre,
					email, pass, pop3host, pop3port, smtphost, smtpport));
						
			this.closeStatementAndConnection();
		} catch (Exception e) {
			e.printStackTrace();
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

	@Override
	public String getGroupMailMateria(String codigoMateria) throws PersistenceException {
		try {
			this.initializeDBMateria();
			int codigo = Integer.parseInt(codigoMateria);
			ResultSet rs = this.statement.executeQuery(String.format("SELECT MailGroup FROM GROUPMAILMATERIA WHERE CodigoMateria=%d;", codigo));
						
			rs.next();
			String mail = rs.getString(1);
			
			rs.close();
			this.closeStatementAndConnection();
			return mail;
		} catch (Exception e) {
			throw new PersistenceException();
		}
	}	
	
	@Override
	public void addGroupMailMateria(String codigoMateria, String mail) throws PersistenceException{
		try {
			int codigo = Integer.parseInt(codigoMateria);
			this.initializeDBMateria();
			this.statement.executeUpdate(String.format("INSERT OR REPLACE INTO GROUPMAILMATERIA (CodigoMateria, MailGroup) VALUES (%d, '%s');", codigo, mail));
						
			this.closeStatementAndConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException();
		}
	}
}
