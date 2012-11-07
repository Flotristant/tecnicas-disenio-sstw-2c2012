package model.factories;

import org.picocontainer.MutablePicoContainer;

import model.Rule;

public class RuleFactory implements IRuleFactory {

	private MutablePicoContainer container;

	public RuleFactory(MutablePicoContainer container) {
		this.container = container;
	}
	
	@Override
	public Rule create(String name) {
		return (Rule) this.container.getComponent(Rule.class + name);
	}

}
