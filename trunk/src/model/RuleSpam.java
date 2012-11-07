package model;

import java.util.regex.Matcher;

/*Regla que no matchea con nada
 */
public class RuleSpam extends Rule{

	public RuleSpam() {
		super();
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
	}
}
