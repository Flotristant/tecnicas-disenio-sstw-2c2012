package persistence.tests;

import junit.framework.Assert;
import model.ActionAltaAlumno;
import model.ActionRule;
import model.factories.tests.mocks.ActionRuleFactoryMock;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.ActionXmlManager;
import persistence.mocks.StudentPersistenceMock;

public class ActionXmlManagerTestCase {
	private ActionRuleFactoryMock actionRuleFactory;
	
	@Before
	public void setUp() {
		this.actionRuleFactory = new ActionRuleFactoryMock();
	}
	
	
	@Test
	public void testShouldGenerateAnXmlElementFromRule() throws Exception
	{
		ActionAltaAlumno action = new ActionAltaAlumno(new StudentPersistenceMock());
		
		Document document = TestUtilities.createDocument();
		
		ActionXmlManager xmlManager = new ActionXmlManager(this.actionRuleFactory);
		
		Element element = xmlManager.getElementFromItem(action, document);
		
		Assert.assertEquals("action", element.getTagName());
		Assert.assertEquals(action.getClass().getSimpleName(), element.getAttribute("name"));
	}
	
	@Test
	public void testShouldGenerateARuleFromXml() throws Exception
	{
		String xml = "<action name='ActionAltaAlumno'/>";
		
		Document document = TestUtilities.loadXMLFromString(xml);
		
		ActionXmlManager xmlManager = new ActionXmlManager(this.actionRuleFactory);
		
		Element actionElement = (Element) document.getElementsByTagName("action").item(0);
		
		ActionRule action = xmlManager.getItemFromXmlElement(actionElement);
		
		Assert.assertEquals("ActionAltaAlumno", action.getClass().getSimpleName());
	}
}
