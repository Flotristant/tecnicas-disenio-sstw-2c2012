package persistence;

import java.sql.ResultSet;
import java.util.ArrayList;

import persistence.exceptions.PersistenceException;

import model.Message;

public class DBTicketPersistence extends DBPersistence implements ITicketPersistence {

	private String pathAttach;
	private Integer ticketId;
	
	private void initializeTicket(String codigoMateria) throws Exception {
		super.initialize(codigoMateria);
		this.pathAttach = codigoMateria + "/Tickets/attachments/";
	}

	@Override
	public Integer createTicket(Message message, String type, String codigoMateria, String tema, String nameAttach) throws PersistenceException {
		try {
			initializeTicket(codigoMateria);
			this.statement.executeUpdate(String.format("INSERT INTO TICKET VALUES (null, '%s', '%s', 'SIN ASIGNAR', NULL, '%s', '%s', '%s')", type, 
					tema, message.getBody(), this.pathAttach + nameAttach, message.getSender()));
			ResultSet rs = this.statement.executeQuery("SELECT last_insert_rowid()");
			
			rs.next();
			this.ticketId = Integer.valueOf(rs.getString(1));
			rs.close();
			this.closeStatementAndConnection();
			return this.ticketId;
		}catch (Exception e) {
			throw new PersistenceException();
		}
	}

	@Override
	public void assignTicket(String codigoMateria, Integer idTicket, String mailAyudante) throws PersistenceException {
		try {
			initializeTicket(codigoMateria);
			this.statement.executeUpdate(String.format("UPDATE TICKET SET Estado='ASIGNADO', " +
						"MailAyudanteAsignado='%s' WHERE Id=%d", mailAyudante, idTicket));	
			closeStatementAndConnection();
		}catch (Exception e) {
			throw new PersistenceException();
		}
	}
	
	@Override
	public void resolveTicket(String codigoMateria, Integer idTicket) throws PersistenceException {
		try {
			initializeTicket(codigoMateria);
			this.statement.executeUpdate(String.format("UPDATE TICKET SET Estado='RESOLVED' " +
						"WHERE Id=%d", idTicket));	
			closeStatementAndConnection();
		}catch (Exception e) {
			throw new PersistenceException();
		}
	}
	
	@Override
	public void closeTicket(String codigoMateria, Integer idTicket) throws PersistenceException {
		try {
			initializeTicket(codigoMateria);
			this.statement.executeUpdate(String.format("UPDATE TICKET SET Estado='CLOSED'" +
						" WHERE Id=%d", idTicket));	
			closeStatementAndConnection();
		}catch (Exception e) {
			throw new PersistenceException();
		}
	}

	@Override
	public void associateMessageToTicket(String codigoMateria, Integer idTicket, Message message) throws PersistenceException{
		try {
			initializeTicket(codigoMateria);
			
			this.statement.executeUpdate(String.format("INSERT INTO ANSWER VALUES (null, '%s', '%s');", message.getBody(), idTicket));
			
			if (senderIsStudent(idTicket, message.getSender()))
				this.statement.executeUpdate(String.format("UPDATE TICKET SET Estado='ASIGNADO' WHERE Id=%d", idTicket));
			else
				this.statement.executeUpdate(String.format("UPDATE TICKET SET Estado='PENDIENTE' WHERE Id=%d", idTicket));
			closeStatementAndConnection();
		}catch (Exception e) {
			throw new PersistenceException();
		}
	}
	
	private boolean senderIsStudent(Integer idTicket, String sender) throws Exception {
		ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM TICKET WHERE Id = %d AND MailAyudanteAsignado = '%s';", idTicket, sender));
		
		rs.next();
		Integer countAyudanteIsSender = Integer.valueOf(rs.getInt(1));
		rs.close();
		return countAyudanteIsSender == 0;
	}

	@Override
	public Iterable<Integer> getUnassignedTickets(String codigoMateria) throws PersistenceException{
		try {
			super.initialize(codigoMateria);
			ResultSet rs = this.statement.executeQuery("SELECT Id FROM TICKET WHERE ESTADO='SIN ASIGNAR';");
			
			ArrayList<Integer> idsTickets = new ArrayList<Integer>();
			while (rs.next())
				idsTickets.add(rs.getInt("Id"));
			
			rs.close();
			closeStatementAndConnection();
			return idsTickets;
		}catch (Exception e) {
			throw new PersistenceException();
		}
	}
	
	@Override
	public boolean isTicketClosed(String codigoMateria, Integer idTicket) throws PersistenceException {
		try {	
			super.initialize(codigoMateria);
			ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM TICKET WHERE ESTADO='CLOSED' AND Id=%d;", idTicket));
		
			rs.next();
			Integer countTicketClosed = Integer.valueOf(rs.getInt(1));
			rs.close();
			this.closeStatementAndConnection();
			return countTicketClosed == 1;
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException();
		}
	}
	
	public boolean isTicketPendiente(String codigoMateria, Integer idTicket) throws PersistenceException {
		try {	
			super.initialize(codigoMateria);
			ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM TICKET WHERE ESTADO='PENDIENTE' AND Id=%d;", idTicket));
		
			rs.next();
			Integer countTicketClosed = Integer.valueOf(rs.getInt(1));
			rs.close();
			this.closeStatementAndConnection();
			return countTicketClosed == 1;
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException();
		}
	}
	
	public boolean isTicketAsignado(String codigoMateria, Integer idTicket) throws PersistenceException {
		try {	
			super.initialize(codigoMateria);
			ResultSet rs = this.statement.executeQuery(String.format("SELECT COUNT(*) FROM TICKET WHERE ESTADO='ASIGNADO' AND Id=%d;", idTicket));
		
			rs.next();
			Integer countTicketClosed = Integer.valueOf(rs.getInt(1));
			rs.close();
			this.closeStatementAndConnection();
			return countTicketClosed == 1;
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException();
		}
	}
}
