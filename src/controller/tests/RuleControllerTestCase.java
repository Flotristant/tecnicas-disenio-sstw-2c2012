package controller.tests;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import controller.RuleController;
import controller.tests.mocks.XmlFileManagerMock;
import controller.tests.mocks.XmlManagerMock;



public class RuleControllerTestCase {

	private XmlFileManagerMock xmlFileManager;

	private XmlManagerMock ruleXmlManager;
	
	@Before
	public void setUp() throws Exception {
		this.xmlFileManager = new XmlFileManagerMock();
		this.ruleXmlManager = new XmlManagerMock(); 
	}
	
	@Test
	public void testShouldGiveCorrectDocumentToXmlManagerWhenCreateController() {
		this.createController();

		Assert.assertEquals(5, this.ruleXmlManager.getCreatedRules().size());
		Assert.assertEquals(this.xmlFileManager.getAttrElement(), this.ruleXmlManager.getDocumentToSave().getAttribute("name"));
	}
	
	private RuleController createController(){
		return new RuleController(this.xmlFileManager, this.ruleXmlManager);
	}
}
