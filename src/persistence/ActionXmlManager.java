package persistence;

import model.ActionRule;
import model.factories.IActionRuleFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ActionXmlManager implements IXmlManager<ActionRule> {

	private IActionRuleFactory actionRuleFactory;

	public ActionXmlManager (IActionRuleFactory actionRuleFactory) {
		this.actionRuleFactory = actionRuleFactory;
		
	}
	
	@Override
	public Element getElementFromItem(ActionRule item, Document document) {
		Element actionElement = document.createElement("action");
		
		actionElement.setAttribute("name", item.getClass().getSimpleName());
		return actionElement;
	}

	@Override
	public ActionRule getItemFromXmlElement(Element actionElement) throws Exception {
		return this.actionRuleFactory.create(actionElement.getAttribute("name"));
	}
}
