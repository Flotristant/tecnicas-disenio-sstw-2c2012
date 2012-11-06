package model;

import java.util.regex.Matcher;

public class RuleEntregaTp extends Rule {

	public RuleEntregaTp(String stringRule) {
		super(stringRule);
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
		super.setTpNumber(matcher.group(1));
	}
}
