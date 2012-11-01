package Model;

import java.util.ArrayList;

public class RuleEntregaTp extends Rule {
	private static final String rule = "\\[ENTREGA-TP-[0-9]+\\]" ;

	public static String getRule() {
		return rule;
	}
	public RuleEntregaTp(){
		this.collectionActions = new ArrayList<ActionRule>();
//		((ArrayList<ActionRule>)collectionActions).add(new ActionValidarEmail(message, mailPersistence))
	}

}
