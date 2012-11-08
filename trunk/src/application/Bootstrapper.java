package application;

import static org.picocontainer.Characteristics.CACHE;

import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionValidateSender;
import model.ActionValidateStudentInGroup;
import model.Rule;
import model.factories.ActionRuleFactory;
import model.factories.IActionRuleFactory;
import model.factories.IRuleFactory;
import model.factories.RuleFactory;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import controller.IRuleController;
import controller.RuleController;
import controller.factories.IRuleControllerFactory;
import controller.factories.RuleControllerFactory;

import persistence.IStudentPersistence;
import persistence.StudentPersistence;

public class Bootstrapper {
	
	private MutablePicoContainer container;

	public MutablePicoContainer getContainer() {
		return this.container;
	}
	
	public void run() {
		this.container = this.createContainer();
		this.configureContainer();
	}
	
	private void configureContainer() {
		this.container
					.as(CACHE).addComponent(MutablePicoContainer.class, this.container)
					.as(CACHE).addComponent(IRuleControllerFactory.class, RuleControllerFactory.class)
					.as(CACHE).addComponent(IRuleController.class, RuleController.class)
					.as(CACHE).addComponent(IRuleFactory.class, RuleFactory.class)
					.addComponent(Rule.class)
					.as(CACHE).addComponent(IActionRuleFactory.class, ActionRuleFactory.class)
					.addComponent(ActionRule.class + "ActionAltaAlumno", ActionAltaAlumno.class)
					.as(CACHE).addComponent(IStudentPersistence.class, StudentPersistence.class)
					.addComponent(ActionRule.class + "ActionSaveTp", ActionSaveTp.class)
					.addComponent(ActionRule.class + "ActionValidateSender", ActionValidateSender.class)
					.addComponent(ActionRule.class + "ActionValidateStudentInGroup", ActionValidateStudentInGroup.class);
	}

	public MutablePicoContainer createContainer() {
		return new DefaultPicoContainer();
	}
}
