package controller.factories.tests.mocks;

import model.Message;
import model.listeners.IResponseMailEventListener;
import controller.IRuleController;

public class RuleControllerMock implements IRuleController {

	@Override
	public void processMessage(Message message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSuscriber(IResponseMailEventListener listener) {
		// TODO Auto-generated method stub
		
	}
}
