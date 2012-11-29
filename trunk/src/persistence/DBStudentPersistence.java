package persistence;

import java.sql.ResultSet;

import persistence.exceptions.PersistenceException;

public class DBStudentPersistence extends DBPersistence implements IStudentPersistence {

	@Override
	public void saveStudent(String codigoMateria, Integer padron, String name,
			String sender) throws PersistenceException {
		//Valida si el alumno esta dado de alta. En caso contrario lo agrega a la DB
		try {
			this.initialize(codigoMateria);
			
			ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM ALUMNO WHERE Padron='%s';", padron));
			
			rs.next();
			Integer count = Integer.valueOf(rs.getString(1));
			
			if(count == 0){
				this.statement.executeUpdate(String.format("INSERT INTO ALUMNO (Padron, Name, Sender) VALUES (%d, '%s', '%s');", padron, name, sender));
			}
			
			rs.close();
			this.closeStatementAndConnection();
			
		} catch (Exception e) {
			throw new PersistenceException();
		}
	}

	@Override
	public boolean validateStudentInGroup(String codigoMateria, Integer padron) throws PersistenceException {
		try {
			this.initialize(codigoMateria);
			
			ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM GROUPALUMNO WHERE Padron=%d;", padron));
						
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
	public boolean validateStudentInCuatrimestre(String codigoMateria,
			Integer padron) throws PersistenceException {
		try {
			this.initialize(codigoMateria);
			ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM ALUMNO WHERE Padron='%s';", padron));
			
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
	public int getNextGroupNumber(String codigoMateria) throws PersistenceException {
		try {
			this.initialize(codigoMateria);
			
			ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM GROUPALUMNO"));
            
            rs.next();
            Integer count = Integer.valueOf(rs.getString(1));
            rs.close();
            if(count == 0){
                    return 1;
            }
			
			
			
			rs = this.statement.executeQuery(String.format("SELECT MAX(GroupNr) FROM GROUPALUMNO"));
			rs.next();
			Integer groupNumber = Integer.valueOf(rs.getString(1));
			rs.close();
			
			this.closeStatementAndConnection();
			return groupNumber + 1;
		} catch (Exception e) {
			throw new PersistenceException();
		}
	}

	@Override
	public void saveStudentInGroup(String codigoMateria, Integer padron,
			int groupNumber) throws PersistenceException {
		try {
			this.initialize(codigoMateria);
			
			this.statement.executeUpdate(String.format("INSERT INTO GROUPALUMNO (Padron, GroupNr) VALUES (%d, %d);", padron, groupNumber));
						
			this.closeStatementAndConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException();
		}
		
	}
}
