package model;

public class ActionCreateTicket extends ActionRule {

	private String type;
	private String codigoMateria;
	private String tema;
	private String nameAttach;
	
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeActions(Rule rule) {
//		Message message, String type, String codigoMateria, String tema, String nameAttach
		this.type = rule.getTypeOfQuery();
		this.codigoMateria = rule.getCodigoMateria();
//		this.tema = rule.getTema();
//		this.nameAttach = rule.ge
		
	}

}
