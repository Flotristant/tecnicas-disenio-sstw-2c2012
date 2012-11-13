package persistence;

import model.Message;

public interface ITicketPersistence {

	public Message getUnassignedTickets(String codigoMateria) throws Exception;
	
	public void createTicket(Message message, boolean publica, String codigoMateria, String tema, String pathAttach) throws Exception;
	
	public void assignTicket(String codigoMateria, String nameAyudante, String titulo, Message message, boolean publica) throws Exception;
	
	public void associateMessageToTicket(String codigoMateria, String titulo, Message message, boolean publica) throws Exception;
	
}
