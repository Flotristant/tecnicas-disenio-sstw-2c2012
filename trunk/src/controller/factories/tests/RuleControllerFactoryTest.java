package controller.factories.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import controller.IRuleController;
import controller.factories.RuleControllerFactory;
import controller.factories.tests.mocks.RuleControllerMock;

import application.tests.mocks.PicoContainerMock;

public class RuleControllerFactoryTest {
	
private PicoContainerMock container;
	
	@Before
	public void setUp() throws Exception {
		this.container = new PicoContainerMock();
	}
	
	@Test
	public void testShouldUseContainerToResolveController(){
		RuleControllerMock controller = new RuleControllerMock();
		this.container.addComponent(IRuleController.class, controller);
		
		RuleControllerFactory factory = this.createRuleControllerFactory();
		Assert.assertSame(controller, factory.create());
	}
	
	public RuleControllerFactory createRuleControllerFactory(){
		return new RuleControllerFactory(this.container);
	}
}
