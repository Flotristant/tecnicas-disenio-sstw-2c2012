package controller;

import model.factories.IRuleFactory;

public class ProjectController {

	private IRuleFactory ruleFactory;

	public ProjectController(IRuleFactory ruleFactory) {
		this.ruleFactory = ruleFactory;
	}
}
