package controller.factories;

import org.picocontainer.MutablePicoContainer;

import controller.IRuleController;

public class RuleControllerFactory implements IRuleControllerFactory{

	private MutablePicoContainer container;
	
	public RuleControllerFactory(MutablePicoContainer container) {
		this.container = container;
	}
	
	@Override
    public IRuleController create() {
		return this.container.getComponent(IRuleController.class);
    }
}
