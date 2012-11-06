package model;

public class RuleConsultaTema extends Rule {
	private static final String rule = "\\[CONSULTA-((PUBLICA)|(PRIVADA))\\] .*" ;

	public static String getRule() {
		return rule;
	}


}
