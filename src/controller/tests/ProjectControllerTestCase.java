package controller.tests;

import org.junit.Before;
import org.junit.Test;

import controller.ProjectController;
import controller.tests.mocks.RuleControllerFactoryMock;
import controller.tests.mocks.XmlFileManagerMock;

public class ProjectControllerTestCase{

	private RuleControllerFactoryMock ruleControllerFactory;

	private XmlFileManagerMock xmlFileManager;
	
	@Before
	public void setUp() throws Exception {
		this.ruleControllerFactory = new RuleControllerFactoryMock();
		this.xmlFileManager = new XmlFileManagerMock();
	}
	
	@Test
	public void testShould() {
		ProjectController projectController = this.createController();
	}
	
	private ProjectController createController(){
		return new ProjectController(this.xmlFileManager, this.ruleControllerFactory);
	}
}
