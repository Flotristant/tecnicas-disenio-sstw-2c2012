package controller.tests;


import junit.framework.Assert;

import model.ActionAltaAlumno;
import model.ActionRule;

import org.junit.Before;
import org.junit.Test;

import persistence.IXmlManager;
import persistence.RuleXmlManager;

import application.Bootstrapper;

import controller.IRuleController;
import controller.RuleController;
import controller.factories.IRuleControllerFactory;
import controller.factories.RuleControllerFactory;
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
	
	@Test
	public void testShouldCreateControllerWithBootstrapper() {
		Bootstrapper bootst = new Bootstrapper();
		bootst.run();
		
		IRuleControllerFactory ruleControllerFactory = bootst.getContainer().getComponent(IRuleControllerFactory.class);
//		IRuleController controller = ruleControllerFactory.create();
		RuleXmlManager altaAlumno =  (RuleXmlManager) bootst.getContainer().getComponent(IXmlManager.class);

//		Assert.assertEquals(5, this.ruleXmlManager.getCreatedRules().size());
//		Assert.assertEquals(this.xmlFileManager.getAttrElement(), this.ruleXmlManager.getDocumentToSave().getAttribute("name"));
	}
	
	private RuleController createController(){
		return new RuleController(this.xmlFileManager, this.ruleXmlManager);
	}
}
