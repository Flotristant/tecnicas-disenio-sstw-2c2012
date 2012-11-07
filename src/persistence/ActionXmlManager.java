package persistence;

import java.lang.reflect.Constructor;

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
		
		actionElement.setAttribute("name", item.getClass().getSimpleName());
		return actionElement;
	}

	@Override
	public ActionRule getItemFromXmlElement(Element actionElement) throws Exception {
		
		// from ale:
		// dejo unas lineas de reflection
		// las reglas me dijo el profesor que tienen que ser clases unicas si solo modifican las regex
		// y las acciones deberian tener una interfaz en comun (un mismo constructor), si realmente podemos enchufarlas en cualquier regla libremente
		// quizas para la persistencia podamos usar singletons para no estar pasandoselas y unificar interfaces
		// me estoy yendo mas tarde veo de adelantar algo
		
		String classname = actionElement.getAttribute("name");
		@SuppressWarnings("unchecked")
		Class<? extends ActionRule> c = (Class<? extends ActionRule>) Class.forName(classname);
		Constructor<? extends ActionRule> cons = null;
		// lo siguiente falla: no usa contructores vacios
		try {
			cons = c.getConstructor();
			ActionRule action = cons.newInstance();
		} catch (Exception e) {
			
		}
		// devolver action
		
		switch (actionElement.getAttribute("name")) {
		case "model.ActionAltaAlumno" : return new ActionAltaAlumno(new StudentPersistence());
		case "model.ActionValidarEmail" : return new ActionValidarEmail(new MailPersistence());
		case "model.ActionSaveTp" : return new ActionSaveTp(new TpPersistence());
		case "model.ActionValidateGroup" : return new ActionValidateGroup();
		case "model.ActionValidateSender" : return new ActionValidateSender(new MailPersistence());
		case "model.ActionValidateStudentInGroup" : return new ActionValidateStudentInGroup(new StudentPersistence());
		}
		return null;
	}
}
