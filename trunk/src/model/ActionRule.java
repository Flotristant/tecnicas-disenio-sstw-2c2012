package model;

public abstract class ActionRule {

	protected Message message;
	
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void initialize(Rule rule, Message message) {
		this.message = message;
		initializeActions(rule);
	}

	protected abstract void initializeActions(Rule rule);
	
}
