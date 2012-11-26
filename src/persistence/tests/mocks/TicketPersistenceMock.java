package persistence.tests.mocks;

import model.Message;
import persistence.ITicketPersistence;
import persistence.exceptions.PersistenceException;

public class TicketPersistenceMock implements ITicketPersistence{

	private String codigoMateria;
	private Integer idTicket;
	private Message message;
	private String tema;
	private String tipo;

	@Override
	public Integer createTicket(Message message, String type,
			String codigoMateria, String tema) throws PersistenceException {
		this.message = message;
		this.tipo = type;
		this.codigoMateria = codigoMateria;
		this.tema = tema;
		return 1;
	}

	@Override
	public void assignTicket(String codigoMateria, Integer idTicket,
			String mailAyudante) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void associateMessageToTicket(String codigoMateria,
			Integer idTicket, Message message) throws PersistenceException {
		this.setCodigoMateria(codigoMateria);
		this.setIdTicket(idTicket);
		this.setMessage(message);
		
	}

	@Override
	public Iterable<Integer> getUnassignedTickets(String codigoMateria)
			throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resolveTicket(String codigoMateria, Integer idTicket)
			throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeTicket(String codigoMateria, Integer idTicket)
			throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTicketClosed(String codigoMateria, Integer idTicket)
			throws PersistenceException {
		if (codigoMateria.equals("7510"))
				return false;
		return true;
	}

	public String getCodigoMateria() {
		return codigoMateria;
	}

	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	public Integer getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getTema() {
		return this.tema;
	}

	public String getTipo() {
		return this.tipo;
	}

}
