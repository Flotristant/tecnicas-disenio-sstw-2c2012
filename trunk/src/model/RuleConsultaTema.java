package model;

import java.util.regex.Matcher;

import persistence.IMateriaPersistence;

public class RuleConsultaTema extends Rule {

	public RuleConsultaTema(IMateriaPersistence  materiaPersistence) throws Exception {
		super(materiaPersistence);
		this.setCodigoMateria(materiaPersistence.getCodigoMateria(null));
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
		super.setTypeOfQuery(matcher.group(1));
	}
}
