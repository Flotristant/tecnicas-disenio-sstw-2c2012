package persistence.tests;

import java.util.ArrayList;

import junit.framework.Assert;

import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionValidateSender;
import model.Rule;
import model.RuleAltaMateria;

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
	
	@Before
	public void setUp() throws Exception {
		this.action1 = new ActionAltaAlumno(new StudentPersistenceMock());
		this.action2 = new ActionSaveTp(new TpPersistenceMock());
		this.action3 = new ActionValidateSender(new MailPersistenceMock());
		this.actionList = new ArrayList<ActionRule>();
	}
	
	@Test
	public void testShouldGenerateAnXmlElementFromRule() throws Exception
	{
		Rule rule = new RuleAltaMateria("pattern1");
		rule.addAction(action1);
		rule.addAction(action2);
		
		Document document = TestUtilities.createDocument();
		
		RuleXmlManager xmlManager = new RuleXmlManager();
		
		Element element = xmlManager.getElementFromItem(rule, document);
		
		Assert.assertEquals("rule", element.getTagName());
		Assert.assertEquals(rule.getPattern(), element.getAttribute("pattern"));
		
		NodeList attributesElement = element.getChildNodes();
		Assert.assertEquals(2, attributesElement.getLength());
		Assert.assertEquals(this.action1.getClass().getSimpleName(), ((Element) attributesElement.item(0)).getAttribute("name"));
		Assert.assertEquals(this.action2.getClass().getSimpleName(), ((Element) attributesElement.item(1)).getAttribute("name"));
	}
	
	@Test
	public void testShouldGenerateARuleFromXml() throws Exception
	{
		String xml = "<rule name='RuleAltaMateria' pattern='pattern1'>" +
				"<action name='ActionAltaAlumno'/><action name='ActionSaveTp'/>" +
				"<action name='ActionValidarEmail'/></rule>";
		
		Document document = TestUtilities.loadXMLFromString(xml);
		
		RuleXmlManager xmlManager = new RuleXmlManager();
		
		Element ruleElement = (Element) document.getElementsByTagName("rule").item(0);
		
		Rule rule = xmlManager.getItemFromXmlElement(ruleElement);
		
		Assert.assertEquals("pattern1", rule.getPattern());
		
		this.actionList = (ArrayList<ActionRule>) rule.getActions();
		
		Assert.assertEquals(3, this.actionList.size());
		Assert.assertEquals(this.action1.getClass().getSimpleName(), this.actionList.get(0).getClass().getSimpleName());
		Assert.assertEquals(this.action2.getClass().getSimpleName(), this.actionList.get(1).getClass().getSimpleName());
		Assert.assertEquals(this.action3.getClass().getSimpleName(), this.actionList.get(2).getClass().getSimpleName());
	}
}
