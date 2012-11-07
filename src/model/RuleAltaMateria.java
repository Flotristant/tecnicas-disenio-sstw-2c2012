package model;

import java.util.regex.Matcher;

public class RuleAltaMateria extends Rule {

	public RuleAltaMateria() {
		super();
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
		super.setCodigoMateria(matcher.group(1));
		super.setPadron(matcher.group(2));
		super.setName(matcher.group(3));
	}
}
