package model.factories;

import model.Rule;

public interface IRuleFactory {
	
	public Rule create(String attribute);
	
}
