package controller;

import controller.factories.IRuleControllerFactory;

import persistence.IXmlFileManager;

public class ProjectController {

	private IRuleControllerFactory ruleControllerFactory;
	private IXmlFileManager xmlFileManager;
	private IRuleController ruleController;

	public ProjectController(IXmlFileManager xmlFileManager, IRuleControllerFactory ruleControllerFactory) {
		this.xmlFileManager = xmlFileManager;
		this.ruleControllerFactory = ruleControllerFactory;
		this.initializeControllers();
	}
	
	private void initializeControllers() {
		this.ruleController = this.ruleControllerFactory.create();
	}
}
