package model.factories.mocks;

import model.Rule;
import model.RuleAltaGrupo;
import model.RuleAltaMateria;
import model.RuleConsultaTema;
import model.RuleEntregaTp;
import model.RuleSpam;
import model.factories.IRuleFactory;

public class RuleFactoryMock implements IRuleFactory {

	@Override
	public Rule create(String name) {
		if (name.equals("RuleAltaMateria"))
			return new RuleAltaMateria();
		if (name.equals("RuleAltaGrupo"))
			return new RuleAltaGrupo();
		if (name.equals("RuleConsultaTema"))
			return new RuleConsultaTema();
		if (name.equals("RuleEntregaTp"))
			return new RuleEntregaTp();
		if (name.equals("RuleSpam"))
			return new RuleSpam();
		return null;
	}

}
