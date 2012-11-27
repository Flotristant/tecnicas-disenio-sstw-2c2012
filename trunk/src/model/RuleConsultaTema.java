package model;

import java.util.regex.Matcher;

import persistence.IMateriaPersistence;

public class RuleConsultaTema extends Rule {

	public RuleConsultaTema(IMateriaPersistence  materiaPersistence) throws Exception {
		super(materiaPersistence);
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
		super.setTypeOfQuery(matcher.group(1));
		super.setTema(matcher.group(4));
	}
}
