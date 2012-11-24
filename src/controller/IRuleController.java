package controller;

import persistence.exceptions.PersistenceException;
import model.Message;
import model.listeners.IResponseMailEventListener;

public interface IRuleController {

	void processMessage(Message message) throws PersistenceException;

	void addSuscriber(IResponseMailEventListener listener);

}
