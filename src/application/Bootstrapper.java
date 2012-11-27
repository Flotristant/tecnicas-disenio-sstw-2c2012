package application;

import static org.picocontainer.Characteristics.CACHE;

import model.ActionAltaAlumno;
import model.ActionConsultaTicket;
import model.ActionCreateTicket;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionSpam;
import model.ActionValidateSender;
import model.ActionValidateAndSaveStudentInGroup;
import model.IRule;
import model.Rule;
import model.RuleAltaGrupo;
import model.RuleAltaMateria;
import model.RuleConsultaTema;
import model.RuleConsultaTicket;
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

import persistence.DBMateriaPersistence;
import persistence.IMailPersistence;
import persistence.IMateriaPersistence;
import persistence.IStudentPersistence;
import persistence.ITicketPersistence;
import persistence.ITpPersistence;
import persistence.DBMailPersistence;
import persistence.DBStudentPersistence;
import persistence.IXmlFileManager;
import persistence.IXmlManager;
import persistence.RuleXmlManager;
import persistence.TicketPersistence;
import persistence.TpPersistence;
import persistence.XmlFileManager;

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
					.as(CACHE).addComponent(IXmlFileManager.class, XmlFileManager.class)
					.addComponent(RuleXmlManager.class)       // no se si le va a gustar
//					.addComponent(class<Integer>.class, RuleXmlManager.class)
					.as(CACHE).addComponent(IRuleFactory.class, RuleFactory.class)
					.as(CACHE).addComponent(IRule.class + "RuleAltaGrupo", RuleAltaGrupo.class)
					.as(CACHE).addComponent(IRule.class + "RuleAltaMateria", RuleAltaMateria.class)
					.as(CACHE).addComponent(IRule.class + "RuleConsultaTema", RuleConsultaTema.class)
					.as(CACHE).addComponent(IRule.class + "RuleEntregaTp", RuleEntregaTp.class)
					.as(CACHE).addComponent(IRule.class + "RuleConsultaTicket", RuleConsultaTicket.class)
					.as(CACHE).addComponent(IRule.class + "RuleSpam", RuleSpam.class)
					.addComponent(IMateriaPersistence.class, DBMateriaPersistence.class)
					.as(CACHE).addComponent(IActionRuleFactory.class, ActionRuleFactory.class)
					.addComponent(ActionRule.class + "ActionAltaAlumno", ActionAltaAlumno.class)
					.addComponent(IStudentPersistence.class, DBStudentPersistence.class)
					.addComponent(ActionRule.class + "ActionSaveTp", ActionSaveTp.class)
					.addComponent(ITpPersistence.class, TpPersistence.class)
					.addComponent(ActionRule.class + "ActionValidateSender", ActionValidateSender.class)
					.addComponent(IMailPersistence.class, DBMailPersistence.class)
					.addComponent(ActionRule.class + "ActionValidateAndSaveStudentInGroup", ActionValidateAndSaveStudentInGroup.class)
					.addComponent(ActionRule.class + "ActionCreateTicket", ActionCreateTicket.class)
					.addComponent(ITicketPersistence.class, TicketPersistence.class)
					.addComponent(ActionRule.class + "ActionConsultaTicket", ActionConsultaTicket.class)
					.addComponent(ActionRule.class + "ActionSpam", ActionSpam.class);
	}

	public MutablePicoContainer createContainer() {
		return new DefaultPicoContainer();
	}
}
