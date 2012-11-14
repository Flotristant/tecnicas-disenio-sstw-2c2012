package controller.tests.mocks;

import controller.IRuleController;
import controller.RuleController;
import controller.factories.IRuleControllerFactory;

public class RuleControllerFactoryMock implements IRuleControllerFactory {

	@Override
	public IRuleController create() {
		return new RuleController(new XmlFileManagerMock(), new XmlManagerMock());
	}

}
