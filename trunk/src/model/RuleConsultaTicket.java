package model;

import java.util.regex.Matcher;

import persistence.IMateriaPersistence;

public class RuleConsultaTicket extends Rule {

	public RuleConsultaTicket(IMateriaPersistence materiaPersistence) {
		super(materiaPersistence);
	}

	@Override
	protected void searchComponentsInSubject(Matcher matcher) {
		super.setTicketId(matcher.group(1));
	}

}
