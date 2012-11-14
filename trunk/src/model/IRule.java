package model;

import model.listeners.IResponseMailEventListener;

public interface IRule {
	
	public void execute(Message message);

	public void setPattern(String pattern);

	public void addAction(ActionRule action);

	public String getPattern();

	public Iterable<ActionRule> getActions();
	
	public void addSubscriber(IResponseMailEventListener listener);
}
