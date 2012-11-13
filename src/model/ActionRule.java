package model;

public abstract class ActionRule {

	protected Message message;
	
	public abstract void execute() throws Exception; 
	
	public void initialize(Rule rule, Message message) {
		this.message = message;
		initializeActions(rule);
	}

	protected abstract void initializeActions(Rule rule);
	
}
