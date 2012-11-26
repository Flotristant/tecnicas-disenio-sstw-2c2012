package persistence.tests;

import java.io.File;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.DBTicketPersistence;
import persistence.exceptions.PersistenceException;

import model.Message;

public class DBTicketPersistenceTestCase {
	
	private final String codigoMateria = "7510";
	private DBTicketPersistence ticket;
	private Message message;
	
	@Before
	public void setUp() {
		this.message = new Message("from","to","subject","body");
		this.ticket = new DBTicketPersistence();
	}
	
	@Test
	public void testShouldCreateTicketCorrectly() throws PersistenceException{
		Assert.assertNotNull(this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test", ""));
	}
	
	@Test
	public void testShouldAssignTicket() throws PersistenceException {
		ArrayList<Integer> idsTicket = new ArrayList<Integer>();
		Integer ticketId = this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test1", "franciso");
		
		for (Integer id : this.ticket.getUnassignedTickets(this.codigoMateria))
			idsTicket.add(id);
		
		Assert.assertEquals(1, idsTicket.size());
		
		idsTicket.clear();
		this.ticket.assignTicket(this.codigoMateria, ticketId, "francisco@Francisco");
		
		for (Integer id : this.ticket.getUnassignedTickets(this.codigoMateria))
			idsTicket.add(id);
		
		Assert.assertEquals(0, idsTicket.size());
	}
	
	@Test
	public void testShouldResolveTicket() throws PersistenceException {
		ArrayList<Integer> idsTicket = new ArrayList<Integer>();
		Integer ticketId = this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test1", "franciso");
		
		for (Integer id : this.ticket.getUnassignedTickets(this.codigoMateria))
			idsTicket.add(id);
		
		Assert.assertEquals(1, idsTicket.size());
		
		idsTicket.clear();
		this.ticket.resolveTicket(this.codigoMateria, ticketId);
		
		for (Integer id : this.ticket.getUnassignedTickets(this.codigoMateria))
			idsTicket.add(id);
		
		Assert.assertEquals(0, idsTicket.size());
	}
	
	@Test
	public void testShouldCloseTicket() throws PersistenceException {
		ArrayList<Integer> idsTicket = new ArrayList<Integer>();
		Integer ticketId = this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test1", "franciso");
		
		for (Integer id : this.ticket.getUnassignedTickets(this.codigoMateria))
			idsTicket.add(id);
		
		Assert.assertEquals(1, idsTicket.size());
		
		this.ticket.closeTicket(this.codigoMateria, ticketId);
		
		Assert.assertTrue(this.ticket.isTicketClosed(this.codigoMateria, ticketId));
	}
	
	@Test
	public void testShouldChangeTicketsStateWhenCorresponds() throws PersistenceException {
		Integer ticketId = this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test1", "franciso");
		this.ticket.assignTicket(this.codigoMateria, ticketId, "francisco@Francisco");
		
		Assert.assertTrue(this.ticket.isTicketAsignado(this.codigoMateria, ticketId));
		
		this.message.setSender("francisco@Francisco");
		this.ticket.associateMessageToTicket(this.codigoMateria, ticketId, this.message);
		
		Assert.assertTrue(this.ticket.isTicketPendiente(this.codigoMateria, ticketId));
		
		this.message.setSender("francisco");
		this.ticket.associateMessageToTicket(this.codigoMateria, ticketId, this.message);
		
		Assert.assertTrue(this.ticket.isTicketAsignado(this.codigoMateria, ticketId));
	}
	
	@Test
	public void testShouldReturnNothingWhenAllTicketsAreAssigned() throws PersistenceException {
		Integer ticketId = this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test1", "franciso");
		this.ticket.assignTicket(this.codigoMateria, ticketId, "francisco@Francisco");
		
		ArrayList<Integer> idsTicket = new ArrayList<Integer>();
		for (Integer id : this.ticket.getUnassignedTickets(this.codigoMateria))
			idsTicket.add(id);
		
		Assert.assertEquals(0, idsTicket.size());
	}
	
	@Test
	public void testShouldReturnEveryUnassignedTickets() throws PersistenceException {
		this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test1", "franciso");
		this.ticket.createTicket(this.message, "privada", this.codigoMateria, "test2", "caty");
		this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test3", "sender");
		
		ArrayList<Integer> idsTicket = new ArrayList<Integer>();
		for (Integer id : this.ticket.getUnassignedTickets(this.codigoMateria))
			idsTicket.add(id);
		
		Assert.assertEquals(3, idsTicket.size());
		
		Assert.assertEquals(Integer.valueOf(1), idsTicket.get(0));
		Assert.assertEquals(Integer.valueOf(2), idsTicket.get(1));
		Assert.assertEquals(Integer.valueOf(3), idsTicket.get(2));
	}
	
	@Test
	public void testShouldReturnCorrectlyNextTicketId() throws PersistenceException{
		this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test1", "franciso");
		this.ticket.createTicket(this.message, "privada", this.codigoMateria, "test2", "caty");
		Assert.assertEquals("3", this.ticket.getNextTicketId(this.codigoMateria));
		this.ticket.createTicket(this.message, "publica", this.codigoMateria, "test3", "sender");
		Assert.assertEquals("4", this.ticket.getNextTicketId(this.codigoMateria));
	}
	
	@After
	public void setDown(){
		File file = new File("7510.db");
		file.delete();
	}

}
