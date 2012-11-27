package model;

import java.util.regex.Matcher;

import persistence.IMateriaPersistence;

public class RuleAltaGrupo extends Rule {

	public RuleAltaGrupo(IMateriaPersistence  materiaPersistence) throws Exception {
		super(materiaPersistence);
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
	}
}
