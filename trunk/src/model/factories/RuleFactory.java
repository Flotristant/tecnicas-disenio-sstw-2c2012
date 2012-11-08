package model.factories;

import org.picocontainer.MutablePicoContainer;

import model.IRule;

public class RuleFactory implements IRuleFactory {

	private MutablePicoContainer container;

	public RuleFactory(MutablePicoContainer container) {
		this.container = container;
	}
	
	@Override
	public IRule create(String name) {
		return (IRule) this.container.getComponent(IRule.class + name);
	}

}
