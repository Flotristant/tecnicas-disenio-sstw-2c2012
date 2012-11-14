package controller;

import java.util.Map;

import controller.factories.IRuleControllerFactory;

import persistence.IXmlFileManager;

public class ProjectController implements IProjectController{

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
		this.ruleController.addSuscriber(this);
	}

	@Override
	public void handleCreatedEvent(String to, String subject, String body,
			Map<String, String> attachments) {
		// TODO Aca enviar mensaje de respuesta para quien sea con el mensaje que sea (y)
		
	}
}
