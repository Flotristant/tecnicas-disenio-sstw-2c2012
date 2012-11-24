package model.factories;

import model.IRule;

public interface IRuleFactory {
	
	public IRule create(String attribute) throws Exception;
	
}
