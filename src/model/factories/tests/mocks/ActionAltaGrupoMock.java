package model.factories.tests.mocks;

import model.ActionRule;
import model.Rule;
import model.listeners.IResponseMailEventListener;

public class ActionAltaGrupoMock extends ActionRule{

	@Override
	public void execute() {
	}

	@Override
	protected void initializeActions(Rule rule) {
	}

	@Override
	protected void addSubscriber(IResponseMailEventListener listener) {
	}

}
