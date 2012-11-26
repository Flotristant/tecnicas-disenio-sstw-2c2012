package model.factories.tests.mocks;

import model.ActionRule;
import model.Rule;
import model.listeners.IResponseMailEventListener;

public class ActionSpamMock extends ActionRule {

	@Override
	public void execute() throws Exception {
		throw new Exception("Spam");

	}

	@Override
	protected void initializeActions(Rule rule) {
	}

	@Override
	protected void addSubscriber(IResponseMailEventListener listener) {
	}

}
