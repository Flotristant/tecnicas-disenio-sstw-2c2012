package model.factories.tests.mocks;

import persistence.DBTpPersistence;
import persistence.DBMailPersistence;
import persistence.DBStudentPersistence;
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
		case "ActionAltaAlumno" : return new ActionAltaAlumno(new DBStudentPersistence());
		case "ActionSaveTp" : return new ActionSaveTp(new DBTpPersistence());
		case "ActionValidateSender" : return new ActionValidateSender(new DBMailPersistence());
		case "ActionValidateStudentInGroup" : return new ActionValidateStudentInGroup(new DBStudentPersistence());
		}
		return null;
	}

}
