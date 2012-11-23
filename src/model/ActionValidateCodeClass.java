package model;

public class ActionValidateCodeClass extends ActionRule {

	private String codeClass;
	
	@Override
	public void execute() throws Exception {
		if (!validateCodeClass())
			throw new Exception("Code doesn't belong to this course");

	}

	private boolean validateCodeClass() {
		String codeToMatch = this.message.getSubject();
		int to = codeToMatch.indexOf("]");
		codeToMatch = codeToMatch.substring(0, to);
		int from = codeToMatch.lastIndexOf("-");
		codeToMatch = codeToMatch.substring(from+1,to);
		if (codeToMatch.equals(this.codeClass))
			return true;
		else return false;
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.codeClass = rule.getCodigoMateria();
	}

}
