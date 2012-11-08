package model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Rule implements IRule {

	private ArrayList<ActionRule> collectionActions;
	protected String pattern;
	private String typeOfQuery;
	private String tpNumber;
	private String codigoMateria;
	private String padron;
	private String name;
	
	public Rule() {
		this.collectionActions = new ArrayList<ActionRule>();
	}
	
	public void setTypeOfQuery(String typeOfQuery) {
		this.typeOfQuery = typeOfQuery;
	}

	public void setTpNumber(String tpNumber) {
		this.tpNumber = tpNumber;
	}

	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	public void setPadron(String padron) {
		this.padron = padron;
	}

	@Override
	public String getPattern() {
		return this.pattern;
	}
	
	@Override
	public void execute(Message message) {
		String subject = message.getSubject();
		Pattern pattern = Pattern.compile(this.pattern);
		Matcher matcher = pattern.matcher(subject);
		if (matcher.matches())
			searchComponentsInSubject(matcher);
		for (ActionRule action : this.collectionActions) {
			action.initialize(this, message);
			action.execute();
		}
	}

	protected abstract void searchComponentsInSubject(Matcher matcher);
	
	@Override
	public Iterable<ActionRule> getActions() {
		return collectionActions;
	}

	@Override
	public void addAction(ActionRule action) {
		this.collectionActions.add(action);
	}

	public String getTypeOfQuery() {
		return this.typeOfQuery;
	}

	public String getTpNumber() {
		return this.tpNumber;
	}

	public String getCodigoMateria() {
		return this.codigoMateria;
	}

	public String getPadron() {
		return this.padron;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
}
