package controller;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IXmlFileManager;
import persistence.IXmlManager;

import model.ActionRule;
import model.Message;
import model.Rule;

public class RuleController implements IRuleController{
	
	private IXmlFileManager xmlFileManager;
	private IXmlManager<Iterable<Rule>> ruleXmlManager;
	private ArrayList<Rule> rules;

	public RuleController(IXmlFileManager xmlFileManager, IXmlManager<Iterable<Rule>> ruleXmlManager) {
		this.xmlFileManager = xmlFileManager;
		this.ruleXmlManager = ruleXmlManager;
		this.rules = new ArrayList<Rule>();
		try {
			this.loadRules();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void processMessage(Message message) {
		for (Rule rule : this.rules) {
			Pattern pattern = Pattern.compile(rule.getPattern());
			if (pattern.matcher(message.getSubject()).matches()) 
				for (ActionRule action : rule.getActions()) {
					action.initialize(rule, message);
					if(!action.execute()) break; //mas adelante tendria que ser otra cosa...
				}
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
