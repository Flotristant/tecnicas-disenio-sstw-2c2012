package model.factories.tests.mocks;

import model.ActionRule;
import model.Rule;

public class ActionSpamMock extends ActionRule {

	@Override
	public void execute() throws Exception {
		throw new Exception("Spam");

	}

	@Override
	protected void initializeActions(Rule rule) {
	}

}
