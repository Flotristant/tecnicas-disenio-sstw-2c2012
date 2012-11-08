package controller;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IXmlFileManager;
import persistence.IXmlManager;

import model.IRule;
import model.Message;
import model.Rule;

public class RuleController implements IRuleController{
	
	private IXmlFileManager xmlFileManager;
	private IXmlManager<Iterable<Rule>> ruleXmlManager;
	private ArrayList<IRule> rules;

	public RuleController(IXmlFileManager xmlFileManager, IXmlManager<Iterable<Rule>> ruleXmlManager) {
		this.xmlFileManager = xmlFileManager;
		this.ruleXmlManager = ruleXmlManager;
		this.rules = new ArrayList<IRule>();
		try {
			this.loadRules();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void processMessage(Message message) {
		for (IRule rule : this.rules) {
			rule.execute(message);
		}
	}
	
	private void loadRules() throws Exception {
		Document document = this.xmlFileManager.read("config/rules");
		
		Element element = document.getDocumentElement();
		
		Iterable<Rule> rules = this.ruleXmlManager.getItemFromXmlElement(element);
		
		for (Rule rule : rules)
			this.rules.add(rule);
	}
	
}
