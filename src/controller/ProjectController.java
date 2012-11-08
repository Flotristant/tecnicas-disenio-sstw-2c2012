package controller;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import controller.factories.IRuleControllerFactory;

import persistence.IXmlFileManager;
import persistence.IXmlManager;
import model.Rule;
import model.factories.IRuleFactory;

public class ProjectController {

	private IRuleControllerFactory ruleControllerFactory;
	private IXmlFileManager xmlFileManager;
	private ArrayList<Rule> rules;

	public ProjectController(IXmlFileManager xmlFileManager, IRuleControllerFactory ruleControllerFactory) {
		this.xmlFileManager = xmlFileManager;
		this.ruleControllerFactory = ruleControllerFactory;
		this.rules = new ArrayList<Rule>();
	}
	
	
	
	
	public ArrayList<Rule> getRules() {
		return rules;
	}
}
