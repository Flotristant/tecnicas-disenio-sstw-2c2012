package persistence;

import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionValidarEmail;
import model.ActionValidateGroup;
import model.ActionValidateSender;
import model.ActionValidateStudentInGroup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ActionXmlManager implements IXmlManager<ActionRule> {

	@Override
	public Element getElementFromItem(ActionRule item, Document document) {
		Element actionElement = document.createElement("action");
		
		actionElement.setAttribute("name", item.getClass().getName());
		return actionElement;
	}

	@Override
	public ActionRule getItemFromXmlElement(Element actionElement) throws Exception {
		
		switch (actionElement.getAttribute("action")) {
		case "ActionAltaAlumno" : return new ActionAltaAlumno(new StudentPersistence());
		case "ActionValidarEmail" : return new ActionValidarEmail(new MailPersistence());
		case "ActionSaveTp" : return new ActionSaveTp(new TpPersistence());
		case "ActionValidateGroup" : return new ActionValidateGroup();
		case "ActionValidateSender" : return new ActionValidateSender(new MailPersistence());
		case "ActionValidateStudentInGroup" : return new ActionValidateStudentInGroup(new StudentPersistence());
		}
		return null;
	}
}
