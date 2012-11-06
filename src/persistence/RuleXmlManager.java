package persistence;

import model.ActionRule;
import model.Rule;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class RuleXmlManager implements IXmlManager<Rule> {

	@Override
	public Element getElementFromItem(Rule item, Document document) {
		
		Element ruleElement = document.createElement("rule");
		
		ruleElement.setAttribute("rule", item.getRule());
		for (ActionRule action : item.getActions())
			ruleElement.appendChild(new ActionXmlManager().
				getElementFromItem(action, document));
		return ruleElement;
	}

	@Override
	public Rule getItemFromXmlElement(Element ruleElement) throws Exception {
		Rule rule = new Rule(ruleElement.getAttribute("rule"));
		
		for (int i = 0; i < ruleElement.getChildNodes().getLength(); i++) {
			Node actionNode = ruleElement.getChildNodes().item(i);
			rule.addAction(new ActionXmlManager().getItemFromXmlElement((Element)actionNode));
		}
		return rule;
	}

}
