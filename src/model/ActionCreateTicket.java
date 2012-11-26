package model;

import persistence.ITicketPersistence;

public class ActionCreateTicket extends ActionRule {

	private String type;
	private String codigoMateria;
	private String tema;
	private Message message;
	private ITicketPersistence ticketPersistence;
	
	public ActionCreateTicket(ITicketPersistence ticketPersistence){
		this.ticketPersistence = ticketPersistence;
	}

	@Override
	public void execute() throws Exception {
		this.ticketPersistence.createTicket(this.message, this.type, this.codigoMateria, this.tema);
		//TODO enviar mail con respuesta y id en subject
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.message = rule.getMessage();
		this.type = rule.getTypeOfQuery();
		this.codigoMateria = rule.getCodigoMateria();
		this.tema = rule.getTema();
	}

}
