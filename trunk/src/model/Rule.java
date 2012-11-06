package model;

import java.util.ArrayList;


public class Rule implements IRule {

	private ArrayList<ActionRule> collectionActions;
	private String rule;
	
	public Rule(String stringRule) {
		this.rule = stringRule;
	}

	public String getRule() {
		return this.rule;
	}
	
	@Override
	public void execute(Message message) {
		// TODO Auto-generated method stub
	}

	public Iterable<ActionRule> getActions() {
		return collectionActions;
	}

	public void addAction(ActionRule action) {
		this.collectionActions.add(action);
	}
}
