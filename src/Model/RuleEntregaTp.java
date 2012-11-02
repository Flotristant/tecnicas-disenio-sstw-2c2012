package Model;

import java.util.ArrayList;

public class RuleEntregaTp extends Rule {
	private static final String rule = "\\[ENTREGA-TP-[0-9]+\\]" ;

	public static String getRule() {
		return rule;
	}
	public RuleEntregaTp(){
		//TODO validar email : que el sender este en la bd y con un grupo asignado. entonces se asigna ese tp a ese numero de grupos
		this.collectionActions = new ArrayList<ActionRule>();
//		((ArrayList<ActionRule>)collectionActions).add(new ActionValidarEmail(message, mailPersistence))
	}

}
