package controller;

import model.Message;
import model.listeners.IResponseMailEventListener;

public interface IRuleController {

	void processMessage(Message message);

	void addSuscriber(IResponseMailEventListener listener);

}
