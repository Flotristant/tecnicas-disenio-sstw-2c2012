package persistence.mocks;

import java.util.Map;

import persistence.ITpPersistence;

public class DBTpPersistenceMock implements ITpPersistence {

	private String codigoMateria;
	private String sender;
	private Integer tpNumber;
	private Map<String, String> attachments;

	@Override
	public void saveTp(String codigoMateria, String sender, Integer tpNumber) {
		this.codigoMateria = codigoMateria;
		this.sender = sender;
		this.tpNumber = tpNumber;
	}

	public String getCodigoMateriaToSave() {
		return this.codigoMateria;
	}

	public Map<String, String> getAttachmentsToSave() {
		return this.attachments;
	}

	public Integer getTpNumberToSave() {
		return this.tpNumber;
	}

	public String getSenderToSave() {
		return this.sender;
	}

	@Override
	public boolean isTPDelivered(String codigoMateria, String sender,
			Integer tpNumber) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
