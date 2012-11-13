package model.factories.tests.mocks;

import persistence.DBTpPersistence;
import persistence.MailPersistence;
import persistence.StudentPersistence;
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
		case "ActionSaveTp" : return new ActionSaveTp(new DBTpPersistence());
		case "ActionValidateSender" : return new ActionValidateSender(new MailPersistence());
		case "ActionValidateStudentInGroup" : return new ActionValidateStudentInGroup(new StudentPersistence());
		}
		return null;
	}

}
