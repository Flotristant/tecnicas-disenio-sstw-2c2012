package controller.tests.mocks;

import java.util.ArrayList;

import model.Rule;
import model.RuleAltaGrupo;
import model.RuleAltaMateria;
import model.RuleEntregaTp;
import model.RuleSpam;
import model.factories.tests.mocks.ActionRuleFactoryMock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IXmlManager;
import persistence.tests.mocks.MateriaPersistenceMock;

public class XmlManagerTestSpamMock implements IXmlManager<Iterable<Rule>> {

	private Element element;
	private ArrayList<Rule> rules;
	private ActionRuleFactoryMock actionFactory;
	private MateriaPersistenceMock materiaPersistence;
	
	public XmlManagerTestSpamMock() {
		this.actionFactory = new ActionRuleFactoryMock();
	}
	
	@Override
	public Element getElementFromItem(Iterable<Rule> item, Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Rule> getItemFromXmlElement(Element element)
			throws Exception {
		this.materiaPersistence = new MateriaPersistenceMock();
		this.element = element;
		
		this.rules = new ArrayList<Rule>();

			Rule rule = new RuleAltaMateria(this.materiaPersistence);
			rule.setPattern("\\[ALTA-MATERIA-([0-9]{4})\\] ([0-9]{5})-(.*)");
			rule.addAction(this.actionFactory.create("ActionAltaAlumnoWithMock"));
			this.rules.add(rule);

			Rule rule2 = new RuleAltaGrupo(this.materiaPersistence);
			rule2.setPattern("\\[ALTA-GRUPO\\]");
			rule2.addAction(this.actionFactory.create("ActionValidateSenderWithMock"));
			rule2.addAction(this.actionFactory.create("ActionValidateStudentInGroupWithMock"));
			this.rules.add(rule2);

//			Rule rule3 = new RuleConsultaTema(this.materiaPersistence);
//			rule3.setPattern("\\[CONSULTA-((PUBLICA)|(PRIVADA))\\] .*");
//			rule3.addAction(this.actionFactory.create("ActionValidateSenderWithMock"));
//			this.rules.add(rule3);

			Rule rule4 = new RuleEntregaTp(this.materiaPersistence);
			rule4.setPattern("\\[ENTREGA-TP-([0-9]+)\\]");
			rule4.addAction(this.actionFactory.create("ActionValidateSenderWithMock"));
			rule4.addAction(this.actionFactory.create("ActionSaveTpWithMock"));
			this.rules.add(rule4);
		
			Rule rule5 = new RuleSpam(this.materiaPersistence);
			rule5.setPattern(".*");
			rule5.addAction(this.actionFactory.create("ActionSpamMock"));
			
			this.rules.add(rule5);
		
			return this.rules;
	}
	
	public Element getDocumentToSave() {
		return this.element;
	}
	
	public ArrayList<Rule> getCreatedRules() {
		return this.rules;
	}


}
