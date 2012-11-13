package application;

import static org.picocontainer.Characteristics.CACHE;

import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionValidateSender;
import model.ActionValidateStudentInGroup;
import model.IRule;
import model.RuleAltaGrupo;
import model.RuleAltaMateria;
import model.RuleConsultaTema;
import model.RuleEntregaTp;
import model.RuleSpam;
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

import persistence.DBTpPersistence;
import persistence.IMailPersistence;
import persistence.IStudentPersistence;
import persistence.ITpPersistence;
import persistence.MailPersistence;
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
					.as(CACHE).addComponent(IRule.class + "RuleAltaGrupo", RuleAltaGrupo.class)
					.as(CACHE).addComponent(IRule.class + "RuleAltaMateria", RuleAltaMateria.class)
					.as(CACHE).addComponent(IRule.class + "RuleConsultaTema", RuleConsultaTema.class)
					.as(CACHE).addComponent(IRule.class + "RuleEntregaTp", RuleEntregaTp.class)
					.as(CACHE).addComponent(IRule.class + "RuleSpam", RuleSpam.class)
					.as(CACHE).addComponent(IActionRuleFactory.class, ActionRuleFactory.class)
					.addComponent(ActionRule.class + "ActionAltaAlumno", ActionAltaAlumno.class)
					.as(CACHE).addComponent(IStudentPersistence.class, StudentPersistence.class)
					.addComponent(ActionRule.class + "ActionSaveTp", ActionSaveTp.class)
					.as(CACHE).addComponent(ITpPersistence.class, DBTpPersistence.class)
					.addComponent(ActionRule.class + "ActionValidateSender", ActionValidateSender.class)
					.as(CACHE).addComponent(IMailPersistence.class, MailPersistence.class)
					.addComponent(ActionRule.class + "ActionValidateStudentInGroup", ActionValidateStudentInGroup.class);
	}

	public MutablePicoContainer createContainer() {
		return new DefaultPicoContainer();
	}
}
