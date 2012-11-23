package controller.tests.mocks;

import controller.IRuleController;
import controller.RuleController;
import controller.factories.IRuleControllerFactory;

public class RuleControllerFactoryTestSpamMock implements IRuleControllerFactory {

	@Override
	public IRuleController create() {
		return new RuleController(new XmlFileManagerMock(), new XmlManagerTestSpamMock());
	}

}
