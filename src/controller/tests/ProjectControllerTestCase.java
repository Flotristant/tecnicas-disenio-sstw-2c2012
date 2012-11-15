package controller.tests;

import org.junit.Before;
import org.junit.Test;

import controller.ProjectController;
import controller.tests.mocks.RuleControllerFactoryMock;

public class ProjectControllerTestCase{

	private RuleControllerFactoryMock ruleControllerFactory;

	@Before
	public void setUp() throws Exception {
		this.ruleControllerFactory = new RuleControllerFactoryMock();
	}
	
	@Test
	public void testShouldCreateControllerCorrectlyWithNoExceptions() {
		this.createController();
	}
	
	private ProjectController createController(){
		return new ProjectController(this.ruleControllerFactory);
	}
}
