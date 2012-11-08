package model.factories.tests;

import junit.framework.Assert;

import model.Rule;
import model.factories.RuleFactory;
import model.factories.tests.mock.RuleAltaGrupoMock;

import org.junit.Before;
import org.junit.Test;

import application.tests.mocks.PicoContainerMock;

public class RuleFactoryTestCase {
	
private PicoContainerMock container;
	
	@Before
	public void setUp() throws Exception {
		this.container = new PicoContainerMock();
	}
	
	@Test
	public void testShouldUseContainerToResolveController(){
		RuleAltaGrupoMock rule = new RuleAltaGrupoMock();
		this.container.addComponent(Rule.class + "", rule);
		
		RuleFactory factory = this.createRuleFactory();
		Assert.assertSame(rule,factory.create(""));
	}
	
	public RuleFactory createRuleFactory(){
		return new RuleFactory(this.container);
	}


}
