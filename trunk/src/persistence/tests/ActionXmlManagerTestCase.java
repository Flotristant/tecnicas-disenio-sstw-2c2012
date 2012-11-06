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
//		Pattern pattern = Pattern.compile("[0-9]{5}");
		Pattern pattern = Pattern.compile("\\[ALTA-MATERIA-[0-9]{2}\\.?[0-9]{2}\\] ([0-9]{5})-.*");
		
		Matcher matcher = pattern.matcher("[ALTA-MATERIA-75.04] 91227-francisco"); 
		
		
		
		if (matcher.matches()) {
			System.out.println(matcher.group(1));
		}
		
		
		
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
