package model;

import java.util.regex.Matcher;

import persistence.IMateriaPersistence;
import persistence.exceptions.PersistenceException;

public class RuleAltaMateria extends Rule {

	public RuleAltaMateria(IMateriaPersistence  materiaPersistence) throws PersistenceException {
		super(materiaPersistence);
		this.setCodigoMateria(materiaPersistence.getCodigoMateria(null));
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
		super.setCodigoMateriaInSubject(matcher.group(1));
		super.setPadron(Integer.valueOf(matcher.group(2)));
		super.setName(matcher.group(3));
	}
}
