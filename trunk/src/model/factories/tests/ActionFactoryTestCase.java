package model.factories.tests;

import junit.framework.Assert;
import model.ActionRule;
import model.factories.ActionRuleFactory;
import model.factories.tests.mock.ActionAltaGrupoMock;

import org.junit.Before;
import org.junit.Test;

import application.tests.mocks.PicoContainerMock;

public class ActionFactoryTestCase {
	
private PicoContainerMock container;
	
	@Before
	public void setUp() throws Exception {
		this.container = new PicoContainerMock();
	}
	
	@Test
	public void testShouldUseContainerToResolveController(){
		ActionAltaGrupoMock action = new ActionAltaGrupoMock();
		this.container.addComponent(ActionRule.class + "", action);
		
		ActionRuleFactory factory = this.createActionFactory();
		Assert.assertSame(action,factory.create(""));
	}
	
	public ActionRuleFactory createActionFactory(){
		return new ActionRuleFactory(this.container);
	}


}
