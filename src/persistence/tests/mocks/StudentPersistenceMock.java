package persistence.tests.mocks;

import java.util.ArrayList;

import persistence.IStudentPersistence;
import persistence.exceptions.PersistenceException;

public class StudentPersistenceMock implements IStudentPersistence {

	
	
	private String codigoMateria;
	private Integer padron;
	private String name;
	private String sender;
	private ArrayList<Integer> padronesGroup;

	public StudentPersistenceMock(){
		this.padronesGroup = new ArrayList<Integer>();
	}
	public String getStudentToSave() {
		return this.name;
	}

	public String getCodigoMateriaToSave() {
		return this.codigoMateria;
	}

	public Integer getPadronToSave() {
		return this.padron;
	}

	public String getSenderToSave() {
		return this.sender;
	}

	@Override
	public void saveStudent(String codigoMateria, Integer padron, String name,
			String sender) {
		this.codigoMateria = codigoMateria;
		this.padron = padron;
		this.name = name;
		this.sender = sender;
	}

	@Override
	public boolean validateStudentInCuatrimestre(String codigoMateria,
			Integer padron) {
		this.padron = padron;
		this.codigoMateria = codigoMateria;
		
		if (padron.equals(10000))
			return false;
		if (padron.equals(88888))
			return false;
		if ((padron.equals(90001)) || padron.equals(90100) || padron.equals(90200) || padron.equals(90300)) {
			return true;
		}
		return true;
	}

	@Override
	public boolean validateStudentInGroup(String codigoMateria, Integer padron) {
		this.codigoMateria = codigoMateria;
		this.padron = padron;
		this.padronesGroup.add(padron);
		if (padron.equals(90000))
			return true;
		if (padron.equals(90300))
			return true;
		return false;
	}

	public void clearPadrones(){
		this.padronesGroup.clear();
	}
	
	public ArrayList<Integer> getPadrones(){
		return this.padronesGroup;
	}
	
	@Override
	public int getNextGroupNumber(String codigoMateria)
			throws PersistenceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void saveStudentInGroup(String codigoMateria, Integer padron,
			int groupNumber) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}


}
