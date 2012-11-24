package model.factories.tests.mocks;

import java.util.regex.Matcher;

import persistence.IMateriaPersistence;

import model.Rule;

public class RuleAltaGrupoMock extends Rule {

	public RuleAltaGrupoMock(IMateriaPersistence materiaPersistence) {
		super(materiaPersistence);
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
		// TODO Auto-generated method stub

	}

}
