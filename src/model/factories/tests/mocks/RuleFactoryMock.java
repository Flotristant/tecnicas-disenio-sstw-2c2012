package model.factories.tests.mocks;

import persistence.tests.mocks.MateriaPersistenceMock;
import model.Rule;
import model.RuleAltaGrupo;
import model.RuleAltaMateria;
import model.RuleConsultaTema;
import model.RuleEntregaTp;
import model.RuleSpam;
import model.factories.IRuleFactory;

public class RuleFactoryMock implements IRuleFactory {
	private MateriaPersistenceMock materiaPersistence;
	@Override
	public Rule create(String name) throws Exception {
		this.materiaPersistence = new MateriaPersistenceMock();
		if (name.equals("RuleAltaMateria"))
			return new RuleAltaMateria(this.materiaPersistence);
		if (name.equals("RuleAltaGrupo"))
			return new RuleAltaGrupo(this.materiaPersistence);
		if (name.equals("RuleConsultaTema"))
			return new RuleConsultaTema(this.materiaPersistence);
		if (name.equals("RuleEntregaTp"))
			return new RuleEntregaTp(this.materiaPersistence);
		if (name.equals("RuleSpam"))
			return new RuleSpam(this.materiaPersistence);
		return null;
	}

}
