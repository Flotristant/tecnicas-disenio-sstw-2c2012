package persistence;

import persistence.exceptions.PersistenceException;
import model.Message;

public interface ITicketPersistence {

	public Integer createTicket(Message message, String type, String codigoMateria, String tema, String nameAttach) throws PersistenceException;
	
	public void assignTicket(String codigoMateria, Integer idTicket, String nameAyudante) throws PersistenceException;
	
	public void associateMessageToTicket(String codigoMateria, Integer idTicket, Message message) throws PersistenceException;

	Iterable<Integer> getUnassignedTickets(String codigoMateria)
			throws PersistenceException;

	void resolveTicket(String codigoMateria, Integer idTicket)
			throws PersistenceException;

	void closeTicket(String codigoMateria, Integer idTicket)
			throws PersistenceException;

	boolean isTicketClosed(String codigoMateria, Integer idTicket) throws PersistenceException;
	
}
