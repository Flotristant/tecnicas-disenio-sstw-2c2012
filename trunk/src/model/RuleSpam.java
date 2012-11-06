package model;

import java.util.regex.Matcher;

/*Regla que no matchea con nada
 */
public class RuleSpam extends Rule{

	public RuleSpam(String stringRule) {
		super(stringRule);
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
	}
}
