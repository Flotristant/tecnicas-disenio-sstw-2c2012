package model;

import java.util.regex.Matcher;

import persistence.IMateriaPersistence;

/*Regla que no matchea con nada
 */
public class RuleSpam extends Rule{

	public RuleSpam(IMateriaPersistence  materiaPersistence) throws Exception {
		super(materiaPersistence);
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
	}
}
