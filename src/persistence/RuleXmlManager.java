package persistence;

import model.ActionRule;
import model.Rule;
import model.factories.IActionRuleFactory;
import model.factories.IRuleFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class RuleXmlManager implements IXmlManager<Rule> {

	private IActionRuleFactory actionRuleFactory;
	private IRuleFactory ruleFactory;

	public RuleXmlManager(IRuleFactory ruleFactory, IActionRuleFactory actionRuleFactory) {
		this.ruleFactory = ruleFactory;
		this.actionRuleFactory = actionRuleFactory;
	}
	
	@Override
	public Element getElementFromItem(Rule item, Document document) {
		
		Element ruleElement = document.createElement("rule");
		ruleElement.setAttribute("name", item.getClass().getSimpleName());
		ruleElement.setAttribute("pattern", item.getPattern());
		
		if (item.getActions() == null) return ruleElement;
		
		for (ActionRule action : item.getActions())
			ruleElement.appendChild(new ActionXmlManager(this.actionRuleFactory).
				getElementFromItem(action, document));
		return ruleElement;
	}

	@Override
	public Rule getItemFromXmlElement(Element ruleElement) throws Exception {
		Rule rule = this.ruleFactory.create(ruleElement.getAttribute("name"));
		rule.setPattern(ruleElement.getAttribute("pattern"));
		
//		switch (ruleElement.getAttribute("name")) {
//		case "RuleAltaGrupo" : rule = new RuleAltaGrupo(ruleElement.getAttribute("pattern"));
//		case "RuleAltaMateria" : rule = new RuleAltaMateria(ruleElement.getAttribute("pattern"));
//		case "RuleConsutltaTema" : rule = new RuleConsultaTema(ruleElement.getAttribute("pattern"));
//		case "RuleEntregaTp" : rule = new RuleEntregaTp(ruleElement.getAttribute("pattern"));
//		case "RuleSpam" : rule = new RuleSpam(ruleElement.getAttribute("pattern"));
//		}
		
		for (int i = 0; i < ruleElement.getChildNodes().getLength(); i++) {
			Node actionNode = ruleElement.getChildNodes().item(i);
			rule.addAction(new ActionXmlManager(this.actionRuleFactory).getItemFromXmlElement((Element)actionNode));
		}
		return rule;
	}

}
