package model;

public class RuleAltaMateria extends Rule {
	private static final String rule = "\\[ALTA-MATERIA-[0-9]{2}\\.?[0-9]{2}\\] [0-9]{5}-.*" ;

	public static String getRule() {
		return rule;
	}

	
	
}
