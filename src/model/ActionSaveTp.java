package model;

import java.util.Map;

import persistence.ITpPersistence;

public class ActionSaveTp extends ActionRule {

	private ITpPersistence tpPersistence;
	private String codigoMateria;
	private String sender;
	private Integer tpNumber;
	private Map<String, String> attachments;

	public ActionSaveTp(ITpPersistence tpPersistence) {
		this.tpPersistence = tpPersistence;
	}
	
	@Override
	public void execute() {
		this.tpPersistence.saveTp(this.codigoMateria, this.sender, this.tpNumber, this.attachments);
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.codigoMateria = rule.getCodigoMateria();
		this.tpNumber = Integer.valueOf(rule.getTpNumber());
		this.sender = this.message.getSender();
		this.attachments = this.message.getAttachments();
	}
	
	
}
