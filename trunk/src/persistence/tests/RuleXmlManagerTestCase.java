package persistence.tests;

import java.util.ArrayList;

import junit.framework.Assert;

import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionValidateSender;
import model.IRule;
import model.Rule;
import model.RuleAltaMateria;
import model.factories.tests.mocks.ActionRuleFactoryMock;
import model.factories.tests.mocks.RuleFactoryMock;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import persistence.RuleXmlManager;
import persistence.mocks.MailPersistenceMock;
import persistence.mocks.StudentPersistenceMock;
import persistence.mocks.TpPersistenceMock;


public class RuleXmlManagerTestCase {

	private ActionAltaAlumno action1;
	private ActionSaveTp action2;
	private ActionValidateSender action3;
	private ArrayList<ActionRule> actionList;
	private ActionRuleFactoryMock actionRuleFactory;
	private RuleFactoryMock ruleFactory;
	
	@Before
	public void setUp() throws Exception {
		this.action1 = new ActionAltaAlumno(new StudentPersistenceMock());
		this.action2 = new ActionSaveTp(new TpPersistenceMock());
		this.action3 = new ActionValidateSender(new MailPersistenceMock());
		this.actionList = new ArrayList<ActionRule>();
		this.actionRuleFactory = new ActionRuleFactoryMock();
		this.ruleFactory = new RuleFactoryMock();
	}
	
	@Test
	public void testShouldGenerateAnXmlElementFromRule() throws Exception
	{
		Rule rule = new RuleAltaMateria();
		rule.setPattern("pattern1");
		rule.addAction(this.action1);
		rule.addAction(this.action2);
		ArrayList<IRule> rules = new ArrayList<IRule>();
		rules.add(rule);
		
		Document document = TestUtilities.createDocument();
		
		RuleXmlManager xmlManager = new RuleXmlManager(this.ruleFactory, this.actionRuleFactory);
		
		Element element = xmlManager.getElementFromItem(rules, document);
		
		Assert.assertEquals("rules", element.getTagName());
		Assert.assertEquals(rule.getPattern(), ((Element) element.getChildNodes().item(0)).getAttribute("pattern"));
		
		NodeList attributesElement = element.getChildNodes().item(0).getChildNodes();
		Assert.assertEquals(2, attributesElement.getLength());
		Assert.assertEquals(this.action1.getClass().getSimpleName(), ((Element) attributesElement.item(0)).getAttribute("name"));
		Assert.assertEquals(this.action2.getClass().getSimpleName(), ((Element) attributesElement.item(1)).getAttribute("name"));
	}
	
	@Test
	public void testShouldGenerateARuleFromXml() throws Exception
	{
		String xml = "<rules><rule name='RuleAltaMateria' pattern='pattern1'>" +
				"<action name='ActionAltaAlumno'/><action name='ActionSaveTp'/>" +
				"<action name='ActionValidateSender'/></rule></rules>";
		
		Document document = TestUtilities.loadXMLFromString(xml);
		
		RuleXmlManager xmlManager = new RuleXmlManager(this.ruleFactory, this.actionRuleFactory);
		
		Element ruleElement = (Element) document.getElementsByTagName("rules").item(0);
		
		Iterable<IRule> rules = xmlManager.getItemFromXmlElement(ruleElement);
		
		IRule rule = ((ArrayList<IRule>) rules).get(0);
		
		Assert.assertEquals("pattern1", rule.getPattern());
		
		this.actionList = (ArrayList<ActionRule>) rule.getActions();
		
		Assert.assertEquals(3, this.actionList.size());
		Assert.assertEquals(this.action1.getClass().getSimpleName(), this.actionList.get(0).getClass().getSimpleName());
		Assert.assertEquals(this.action2.getClass().getSimpleName(), this.actionList.get(1).getClass().getSimpleName());
		Assert.assertEquals(this.action3.getClass().getSimpleName(), this.actionList.get(2).getClass().getSimpleName());
	}
}
