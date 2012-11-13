package persistence;

import model.Message;

public interface ITicketPersistence {

	public Message getUnassignedTickets(String codigoMateria) throws Exception;
	
	public void createTicket(Message message, boolean publica, String codigoMateria, String tema, String pathAttach) throws Exception;
	
	public void assignTicket(Message message, boolean publica, String codigoMateria);
	
	public void associateMessageToTicket(Message message, boolean publica, String codigoMateria);
	
}
