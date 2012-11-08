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

	@Override
	public Element getElementFromItem(Iterable<Rule> item, Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Rule> getItemFromXmlElement(Element element)
			throws Exception {
		Rule rule1 = new RuleAltaGrupo();
		rule1.setPattern("\\[ALTA-GRUPO\\]");
		Rule rule2 = new RuleAltaMateria();
		rule2.setPattern("\\[ALTA-MATERIA-([0-9]{4})\\] ([0-9]{5})-(.*)");
		Rule rule3 = new RuleConsultaTema();
		rule3.setPattern("\\[CONSULTA-((PUBLICA)|(PRIVADA))\\] .*");
		Rule rule4 = new RuleEntregaTp();
		rule4.setPattern("\\[ENTREGA-TP-([0-9]+)\\]");
		Rule rule5 = new RuleSpam();
		rule5.setPattern(".*");
		
		ArrayList<Rule> rules = new ArrayList<Rule>();
		rules.add(rule1);
		rules.add(rule2);
		rules.add(rule3);
		rules.add(rule4);
		rules.add(rule5);
		return rules;
	}


}
