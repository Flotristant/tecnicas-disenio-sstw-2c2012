package model;

import java.util.ArrayList;
import java.util.List;

import model.listeners.IResponseMailEventListener;
import persistence.ITicketPersistence;

public class ActionCreateTicket extends ActionRule {

	private String type;
	private String codigoMateria;
	private String tema;
	private Message message;
	private ITicketPersistence ticketPersistence;
	private List<IResponseMailEventListener> listeners;
	
	public ActionCreateTicket(ITicketPersistence ticketPersistence){
		this.ticketPersistence = ticketPersistence;
		this.listeners = new ArrayList<IResponseMailEventListener>();
	}

	@Override
	public void execute() throws Exception {
		Integer ticketId = this.ticketPersistence.createTicket(this.message, this.type, this.codigoMateria, this.tema);
		
			
		for(IResponseMailEventListener listener : this.listeners) {
			listener.handleCreatedEvent(message, "Se ha creado el ticket numero " + ticketId + " del tema " + this.tema);
			if(type.equals("PUBLICA")) {
				this.message.setSender(this.message.getTo());
				listener.handleCreatedEvent(message, "Se ha creado el ticket numero " + ticketId + " del tema " + this.tema);
			}
		}
	}

	@Override
	protected void initializeActions(Rule rule) {
		this.message = rule.getMessage();
		this.type = rule.getTypeOfQuery();
		this.codigoMateria = rule.getCodigoMateria();
		this.tema = rule.getTema();
	}

	@Override
	protected void addSubscriber(IResponseMailEventListener listener) {
		this.listeners.add(listener);	
	}

}
