package model;

import persistence.exceptions.PersistenceException;
import model.listeners.IResponseMailEventListener;

public interface IRule {
	
	public void execute(Message message) throws PersistenceException;

	public void setPattern(String pattern);

	public void addAction(ActionRule action);

	public String getPattern();

	public Iterable<ActionRule> getActions();
	
	public void addSubscriber(IResponseMailEventListener listener);
}
