package model.factories;

import model.ActionRule;

import org.picocontainer.MutablePicoContainer;

public class ActionRuleFactory implements IActionRuleFactory {

	private MutablePicoContainer container;
	
	public ActionRuleFactory(MutablePicoContainer container) {
		this.container = container;
	}
	
	@Override
    public ActionRule create(String action) {
		return (ActionRule) this.container.getComponent(ActionRule.class + action);
    }
}
