package Model;

import java.util.Map;

import persistence.ITpPersistence;

public class ActionSaveTp extends ActionRule {

	private String codigoMateria;
	private String sender;
	private Integer tpNumber;
	private Map<String, byte[]> attachments;
	private ITpPersistence tpPersistence;

	public ActionSaveTp(String codigoMateria, String sender, Integer tpNumber, Map<String,byte[]> attachments, ITpPersistence tpPersistence) {
		this.codigoMateria = codigoMateria;
		this.sender = sender;
		this.tpNumber = tpNumber;
		this.attachments = attachments;
		this.tpPersistence = tpPersistence;
	}
	
	@Override
	public boolean execute() {
		this.tpPersistence.saveTp(this.codigoMateria, this.sender, this.tpNumber, this.attachments);
		return true;
	}
}
