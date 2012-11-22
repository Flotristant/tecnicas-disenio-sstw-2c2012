package controller.tests.mocks;

import persistence.TpPersistence;
import persistence.tests.mocks.*;
import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionValidateSender;
import model.ActionValidateStudentInGroup;
import model.factories.IActionRuleFactory;

public class ControllerActionRuleFactoryMock implements IActionRuleFactory{

	@Override
	public ActionRule create(String attribute) {
		switch (attribute) {
		case "ActionAltaAlumno" : return new ActionAltaAlumno(new ControllerStudentPersistenceMock());
		case "ActionSaveTp" : return new ActionSaveTp(new ControllerTpPersistenceMock());
		case "ActionValidateSender" : return new ActionValidateSender(new ControllerMailPersistenceMock());
		case "ActionValidateStudentInGroup" : return new ActionValidateStudentInGroup(new ControllerStudentPersistenceMock());
		}
		return null;
	}

}
