package controller.tests.mocks;

import java.util.ArrayList;

import model.Rule;
import model.RuleAltaMateria;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IXmlManager;

public class XmlManagerMock implements IXmlManager<Iterable<Rule>> {

	private Element element;
	private ArrayList<Rule> rules;

	@Override
	public Element getElementFromItem(Iterable<Rule> item, Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Rule> getItemFromXmlElement(Element element)
			throws Exception {
		this.element = element;
		
		this.rules = new ArrayList<Rule>();
		
		if (element.getAttribute("name").equals("RuleAltaMateria")) {
			Rule rule = new RuleAltaMateria();
			rule.setPattern("\\[ALTA-MATERIA-([0-9]{4})\\] ([0-9]{5})-(.*)");
			this.rules.add(rule);
		}
		return this.rules;
	}
	
	public Element getDocumentToSave() {
		return this.element;
	}
	
	public ArrayList<Rule> getCreatedRules() {
		return this.rules;
	}


}
