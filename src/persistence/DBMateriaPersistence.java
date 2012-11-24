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
	public void addMateria(int codigoMateria, String email) throws PersistenceException {
		try {
			this.initializeDBMateria();
			
			this.statement.executeUpdate(String.format("INSERT INTO MATERIAS (CodigoMateria, Email) VALUES (%d, '%s');", codigoMateria, email));
						
			this.closeStatementAndConnection();
		} catch (Exception e) {
			throw new PersistenceException();
		}
		
	}
	
	

}
