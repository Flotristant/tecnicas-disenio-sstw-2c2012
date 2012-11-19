package controller.tests.mocks;

import java.util.ArrayList;

import model.Rule;
import model.RuleAltaGrupo;
import model.RuleAltaMateria;
import model.RuleConsultaTema;
import model.RuleEntregaTp;
import model.RuleSpam;

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
		
	//	if (element.getAttribute("name").equals("RuleAltaMateria")) {
			Rule rule = new RuleAltaMateria();
			rule.setPattern("\\[ALTA-MATERIA-([0-9]{4})\\] ([0-9]{5})-(.*)");
			this.rules.add(rule);
//		}
		
		//if (element.getAttribute("name").equals("RuleAltaGrupo")) {
			Rule rule2 = new RuleAltaGrupo();
			rule2.setPattern("\\[ALTA-GRUPO\\]");
			this.rules.add(rule2);
	//	}
		
		//if (element.getAttribute("name").equals("RuleConsultaTema")) {
			Rule rule3 = new RuleConsultaTema();
			rule3.setPattern("\\[CONSULTA-((PUBLICA)|(PRIVADA))\\] .*");
			this.rules.add(rule3);
	//	}
		
		//if (element.getAttribute("name").equals("RuleEntregaTp")) {
			Rule rule4 = new RuleEntregaTp();
			rule4.setPattern("\\[ENTREGA-TP-([0-9]+)\\]");
			this.rules.add(rule4);
		//}
		
		//if (element.getAttribute("name").equals("RuleSpam")) {
			Rule rule5 = new RuleSpam();
			rule5.setPattern(".*");
			this.rules.add(rule5);
		//}
		return this.rules;
	}
	
	public Element getDocumentToSave() {
		return this.element;
	}
	
	public ArrayList<Rule> getCreatedRules() {
		return this.rules;
	}


}
