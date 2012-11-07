package persistence;

import java.lang.reflect.Constructor;

import model.ActionAltaAlumno;
import model.ActionRule;
import model.ActionSaveTp;
import model.ActionValidateSender;
import model.ActionValidateStudentInGroup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ActionXmlManager implements IXmlManager<ActionRule> {

	@Override
	public Element getElementFromItem(ActionRule item, Document document) {
		Element actionElement = document.createElement("action");
		
		actionElement.setAttribute("name", item.getClass().getSimpleName());
		return actionElement;
	}

	@Override
	public ActionRule getItemFromXmlElement(Element actionElement) throws Exception {
//		
//		String classname = actionElement.getAttribute("name");
//		@SuppressWarnings("unchecked")
//		Class<? extends ActionRule> c = (Class<? extends ActionRule>) Class.forName(classname);
//		Constructor<? extends ActionRule> cons = c.getConstructor();
//		ActionRule action = cons.newInstance();
		// devolver action
		
		switch (actionElement.getAttribute("name")) {
		case "ActionAltaAlumno" : return new ActionAltaAlumno(new StudentPersistence());
		case "ActionValidarEmail" : return new ActionValidateSender(new MailPersistence());
		case "ActionSaveTp" : return new ActionSaveTp(new TpPersistence());
		case "ActionValidateSender" : return new ActionValidateSender(new MailPersistence());
		case "ActionValidateStudentInGroup" : return new ActionValidateStudentInGroup(new StudentPersistence());
		}
		return null;
	}
}
