package model;

import java.util.regex.Matcher;

import persistence.IMateriaPersistence;

public class RuleEntregaTp extends Rule {

	public RuleEntregaTp(IMateriaPersistence  materiaPersistence) throws Exception {
		super(materiaPersistence);
		
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
		super.setTpNumber(matcher.group(1));
	}
}
