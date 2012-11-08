package model;

public interface IRule {
	
	public void execute(Message message);

	public void setPattern(String pattern);

	public void addAction(ActionRule action);

	public String getPattern();

	public Iterable<ActionRule> getActions();
}
