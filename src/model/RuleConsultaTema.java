package model;

import java.util.regex.Matcher;

public class RuleConsultaTema extends Rule {

	public RuleConsultaTema(String stringRule) {
		super(stringRule);
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
		super.setTypeOfQuery(matcher.group(1));
	}
}
