package model;

import model.listeners.IResponseMailEventListener;
import persistence.ITicketPersistence;

public class ActionConsultaTicket extends ActionRule {

	private String ticketId;
	private ITicketPersistence ticketPersistence;
	private String codigoMateria;
	
	public ActionConsultaTicket(ITicketPersistence ticketPersistence){
		this.ticketPersistence = ticketPersistence;
	}

	@Override
	public void execute() throws Exception {
		if (this.ticketPersistence.isTicketClosed(codigoMateria, Integer.valueOf(ticketId)))
				throw new Exception("Ticket is closed");
		this.ticketPersistence.associateMessageToTicket(this.codigoMateria, Integer.valueOf(this.ticketId), this.message);
		
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.ticketId = rule.getTicketId();
		this.codigoMateria = rule.getCodigoMateria();
	}

	@Override
	protected void addSubscriber(IResponseMailEventListener listener) {
	}

}
