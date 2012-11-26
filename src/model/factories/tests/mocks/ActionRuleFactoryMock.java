package model.factories.tests.mocks;

import persistence.TpPersistence;
import persistence.tests.mocks.*;
import persistence.DBMailPersistence;
import persistence.DBStudentPersistence;
import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionValidateSender;
import model.ActionValidateAndSaveStudentInGroup;
import model.factories.IActionRuleFactory;

public class ActionRuleFactoryMock implements IActionRuleFactory{

	@Override
	public ActionRule create(String attribute) {
		switch (attribute) {
		case "ActionAltaAlumno" : return new ActionAltaAlumno(new DBStudentPersistence());
		case "ActionAltaAlumnoWithMock" : return new ActionAltaAlumno(new StudentPersistenceMock());
		
		case "ActionSaveTp" : return new ActionSaveTp(new TpPersistence());
		case "ActionSaveTpWithMock" : return new ActionSaveTp(new TpPersistenceMock());
		
		case "ActionValidateSender" : return new ActionValidateSender(new DBMailPersistence());
		case "ActionValidateSenderWithMock" : return new ActionValidateSender(new MailPersistenceMock());
		
		case "ActionValidateStudentInGroup" : return new ActionValidateAndSaveStudentInGroup(new DBStudentPersistence());
		case "ActionValidateStudentInGroupWithMock" : return new ActionValidateAndSaveStudentInGroup(new StudentPersistenceMock());
		
		
		case "ActionSpamMock": return new ActionSpamMock();
		}
		return null;
	}

}
