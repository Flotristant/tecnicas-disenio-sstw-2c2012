package Controller;

import java.util.regex.Pattern;

import Model.IRule;
import Model.RuleAltaGrupo;
import Model.RuleAltaMateria;
import Model.RuleConsultaTema;
import Model.RuleEntregaTp;
import Model.RuleSpam;

public class PatternRule {
	public IRule searchPattern(String text)
	{
		Pattern pattern = Pattern.compile(RuleAltaMateria.getRule());
		if (pattern.matcher(text).matches())
			return new RuleAltaMateria();
		
		pattern = Pattern.compile(RuleEntregaTp.getRule());
		if (pattern.matcher(text).matches())
			return new RuleEntregaTp();
		
		pattern = Pattern.compile(RuleConsultaTema.getRule());
		if (pattern.matcher(text).matches())
			return new RuleConsultaTema();
		
		pattern = Pattern.compile(RuleAltaGrupo.getRule());
		if (pattern.matcher(text).matches())
			return new RuleAltaGrupo();
		
		return new RuleSpam();
	}
	
}
