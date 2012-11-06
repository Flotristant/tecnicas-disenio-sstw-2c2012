package model;

import java.util.Map;

import persistence.ITpPersistence;

public class ActionSaveTp extends ActionRule {

	private ITpPersistence tpPersistence;
	private String codigoMateria;
	private String sender;
	private Integer tpNumber;
	private Map<String, byte[]> attachments;
	public void setTpNumber(Integer tpNumber) {
		this.tpNumber = tpNumber;
	}

	public ActionSaveTp(ITpPersistence tpPersistence) {
		this.tpPersistence = tpPersistence;
	}
	
	public void setAttachments(Map<String, byte[]> attachments) {
		this.attachments = attachments;
	}

	@Override
	public boolean execute() {
		this.tpPersistence.saveTp(this.codigoMateria, this.sender, this.tpNumber, this.attachments);
		return true;
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.codigoMateria = rule.getCodigoMateria();
		this.tpNumber = Integer.valueOf(rule.getTpNumber());
		this.sender = this.message.getSender();
		this.attachments = this.message.getAttachments();
	}
	
	
}
