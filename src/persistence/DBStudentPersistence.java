package persistence;

import java.sql.ResultSet;

import persistence.exceptions.PersistenceException;

public class DBStudentPersistence extends DBPersistence implements IStudentPersistence {

	@Override
	public void saveStudent(String codigoMateria, Integer padron, String name,
			String sender) throws PersistenceException {
		try {
			this.initialize(codigoMateria);
			
			this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Name, Sender) VALUES (%d, '%s', '%s');", padron, name, sender));
						
			this.closeStatementAndConnection();
		} catch (Exception e) {
			throw new PersistenceException();
		}
	}

	@Override
	public boolean validateStudentInGroup(String codigoMateria, Integer padron) throws PersistenceException {
		try {
			this.initialize(codigoMateria);
			
			ResultSet rs = this.statement.executeQuery(String.format("SELECT GroupNr FROM GROUPALUMNO WHERE Padron='%s';", padron));
						
			int count = 0;
			while (rs.next()) {
				count++;
			}
			rs.close();
			this.closeStatementAndConnection();
			return count == 0;
		} catch (Exception e) {
			throw new PersistenceException();
		}
	}

	@Override
	public boolean validateStudentInCuatrimestre(String codigoMateria,
			Integer padron) throws PersistenceException {
		try {
			this.initialize(codigoMateria);
			ResultSet rs = this.statement.executeQuery(String.format("SELECT Padron FROM ALUMNO WHERE Padron='%s';", padron));
			
			int count = 0;
			while (rs.next()) {
				count++;
			}
			rs.close();
			this.closeStatementAndConnection();
			return count == 1;
		} catch (Exception e) {
			throw new PersistenceException();
		}
	}
}
