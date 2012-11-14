package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.Message;

import controller.factories.IRuleControllerFactory;

import persistence.IXmlFileManager;

public class ProjectController implements IProjectController{

	private IRuleControllerFactory ruleControllerFactory;
	private IXmlFileManager xmlFileManager;
	private IRuleController ruleController;
	
	private List<model.Message> anwserMessages;

	public ProjectController(IXmlFileManager xmlFileManager, IRuleControllerFactory ruleControllerFactory) {
		this.xmlFileManager = xmlFileManager;
		this.ruleControllerFactory = ruleControllerFactory;
		this.initializeControllers();
		this.anwserMessages = null;
	}
	
	private void initializeControllers() {
		this.ruleController = this.ruleControllerFactory.create();
		this.ruleController.addSuscriber(this);
	}

	@Override
	public void handleCreatedEvent( model.Message m, String subject) {
			if (this.anwserMessages == null) {
				this.anwserMessages = new ArrayList<Message>();
			}
			model.Message answerMessage = new model.Message(m.getTo(), m.getSender(), subject, m.getBody());
			if (m.isWithAttachments()) {
				answerMessage.addAttachments(m.getAttachments());
			}
			this.anwserMessages.add(answerMessage);
	}
	
	public List<model.Message> processIncoming(List<model.Message> messagesIncoming) {
		Iterator<model.Message> it = messagesIncoming.iterator();
		while (it.hasNext()) {
			model.Message m = it.next();
			this.ruleController.processMessage(m);
		}
		
		List<model.Message> returnMessages = this.anwserMessages;
		this.anwserMessages = null;
		return returnMessages;		
	}
}
