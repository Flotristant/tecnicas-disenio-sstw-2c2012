package persistence.mocks;

import java.util.Map;

import persistence.ITpPersistence;

public class TpPersistenceMock implements ITpPersistence {

	private String codigoMateria;
	private String sender;
	private Integer tpNumber;
	private Map<String, byte[]> attachments;

	@Override
	public void saveTp(String codigoMateria, String sender, Integer tpNumber,
			Map<String, byte[]> attachments) {
		this.codigoMateria = codigoMateria;
		this.sender = sender;
		this.tpNumber = tpNumber;
		this.attachments = attachments;
	}

	public String getCodigoMateriaToSave() {
		return this.codigoMateria;
	}

	public Map<String, byte[]> getAttachmentsToSave() {
		return this.attachments;
	}

	public Integer getTpNumberToSave() {
		return this.tpNumber;
	}

	public String getSenderToSave() {
		return this.sender;
	}

}
