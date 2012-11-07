package model.factories.mocks;

import persistence.MailPersistence;
import persistence.StudentPersistence;
import persistence.TpPersistence;
import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionValidateSender;
import model.ActionValidateStudentInGroup;
import model.factories.IActionRuleFactory;

public class ActionRuleFactoryMock implements IActionRuleFactory{

	@Override
	public ActionRule create(String attribute) {
		switch (attribute) {
		case "ActionAltaAlumno" : return new ActionAltaAlumno(new StudentPersistence());
		case "ActionValidarEmail" : return new ActionValidateSender(new MailPersistence());
		case "ActionSaveTp" : return new ActionSaveTp(new TpPersistence());
		case "ActionValidateSender" : return new ActionValidateSender(new MailPersistence());
		case "ActionValidateStudentInGroup" : return new ActionValidateStudentInGroup(new StudentPersistence());
		}
		return null;
	}

}
