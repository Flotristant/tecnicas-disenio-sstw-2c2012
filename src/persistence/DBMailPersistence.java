package persistence;

import java.sql.ResultSet;

import persistence.exceptions.PersistenceException;

public class DBMailPersistence extends DBPersistence implements IMailPersistence {

	@Override
	public boolean existsMail(String codigoMateria, String direccionEmail) throws PersistenceException{
		try {
			this.initialize(codigoMateria);
			ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM ALUMNO WHERE Sender='%s';", direccionEmail));
			
			rs.next();
			Integer count = Integer.valueOf(rs.getString(1));
			
			rs.close();
			this.closeStatementAndConnection();
			return count == 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException();
		}
	}
}
