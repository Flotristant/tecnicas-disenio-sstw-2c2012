package model.factories.mocks;

import model.Rule;
import model.RuleAltaMateria;
import model.factories.IRuleFactory;

public class RuleFactoryMock implements IRuleFactory {

	@Override
	public Rule create(String name) {
		if (name.equals("RuleAltaMateria"))
			return new RuleAltaMateria();
		return null;
	}

}
