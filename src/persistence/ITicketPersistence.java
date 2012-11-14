package persistence;

import persistence.exceptions.PersistenceException;
import model.Message;

public interface ITicketPersistence {

	public Message getUnassignedTickets(String codigoMateria) throws PersistenceException;
	
	public void createTicket(Message message, boolean publica, String codigoMateria, String tema, String pathAttach) throws PersistenceException;
	
	public void assignTicket(String codigoMateria, String nameAyudante, String titulo, Message message, boolean publica) throws PersistenceException;
	
	public void associateMessageToTicket(String codigoMateria, String titulo, Message message, boolean publica) throws PersistenceException;
	
}
