package model;

import java.util.ArrayList;


public class Rule implements IRule {

	private ArrayList<ActionRule> collectionActions;
	private String rule;
	
	public Rule(String stringRule) {
		this.rule = stringRule;
		this.collectionActions = new ArrayList<ActionRule>();
	}

	public String getPattern() {
		return this.rule;
	}
	
	@Override
	public void execute(Message message) {
		for (ActionRule action : this.collectionActions)
			action.execute();
	}

	public Iterable<ActionRule> getActions() {
		return collectionActions;
	}

	public void addAction(ActionRule action) {
		this.collectionActions.add(action);
	}
}
