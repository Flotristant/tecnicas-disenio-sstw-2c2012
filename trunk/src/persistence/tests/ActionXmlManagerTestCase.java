package persistence.tests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;
import model.ActionAltaAlumno;
import model.ActionRule;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.ActionXmlManager;
import persistence.mocks.StudentPersistenceMock;

public class ActionXmlManagerTestCase {
	
	@Test
	public void testShouldGenerateAnXmlElementFromRule() throws Exception
	{
		ActionAltaAlumno action = new ActionAltaAlumno(new StudentPersistenceMock());
		
		Document document = TestUtilities.createDocument();
		
		ActionXmlManager xmlManager = new ActionXmlManager();
		
		Element element = xmlManager.getElementFromItem(action, document);
		
		Assert.assertEquals("action", element.getTagName());
		Assert.assertEquals(action.getClass().getSimpleName(), element.getAttribute("name"));
	}
	
	@Test
	public void testShouldGenerateARuleFromXml() throws Exception
	{
		String xml = "<action name='ActionAltaAlumno'/>";
		
		Document document = TestUtilities.loadXMLFromString(xml);
		
		ActionXmlManager xmlManager = new ActionXmlManager();
		
		Element actionElement = (Element) document.getElementsByTagName("action").item(0);
		
		ActionRule action = xmlManager.getItemFromXmlElement(actionElement);
		
		Assert.assertEquals("ActionAltaAlumno", action.getClass().getSimpleName());
	}
}
