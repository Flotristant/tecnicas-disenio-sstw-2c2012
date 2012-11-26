package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import model.Message;
import persistence.exceptions.PersistenceException;

public class TicketPersistence implements ITicketPersistence {

	private DBTicketPersistence dataBaseTicket;

	public TicketPersistence() {
		this.dataBaseTicket = new DBTicketPersistence();
	}
	
	@Override
	public Integer createTicket(Message message, String type, String codigoMateria, String tema)
			throws PersistenceException {
		String idTicket = this.saveTicketAttachments(message.getAttachments(), codigoMateria);
		return dataBaseTicket.createTicket(message, type, codigoMateria, tema, idTicket);
	}
	
	@Override
	public void associateMessageToTicket(String codigoMateria,
			Integer idTicket, Message message) throws PersistenceException {
		dataBaseTicket.associateMessageToTicket(codigoMateria, idTicket, message);
	}
	
	
	public String saveTicketAttachments(HashMap<String, String> attachments, String codigoMateria) throws PersistenceException{
		
		String pathToSave;
		String ticketId = dataBaseTicket.getNextTicketId(codigoMateria);
		
		pathToSave = "./" + codigoMateria + "/Tickets/attachments/" + ticketId + "/";
		File dirs = new File(pathToSave);
		if (!dirs.mkdir())
			dirs.mkdirs();
		for (String key : attachments.keySet()) {
			String path = attachments.get(key);
			
			try {
				File inFile = new File(path + key);
				File outFile = new File(pathToSave + key);
				outFile.createNewFile();
				
				FileInputStream in = new FileInputStream(inFile);
				FileOutputStream out = new FileOutputStream(outFile);

				int byteOfData;
				while( (byteOfData = in.read() ) != -1)
					out.write(byteOfData);
				
				in.close();
				out.close();
				inFile.delete();
			} catch(IOException e) {
				e.printStackTrace();
				System.err.println("Hubo un error de entrada/salida");
			}
		}
		return ticketId;
		
	}
	
	@Override
	public boolean isTicketClosed(String codigoMateria, Integer idTicket)
			throws PersistenceException {
		return dataBaseTicket.isTicketClosed(codigoMateria, idTicket);
	}

	/*
	 * GUI
	 */
	@Override
	public void assignTicket(String codigoMateria, Integer idTicket,
			String mailAyudante) throws PersistenceException {
		dataBaseTicket.assignTicket(codigoMateria, idTicket, mailAyudante);
	}

	@Override
	public Iterable<Integer> getUnassignedTickets(String codigoMateria)
			throws PersistenceException {
		return dataBaseTicket.getUnassignedTickets(codigoMateria);
	}
	
	@Override
	public void resolveTicket(String codigoMateria, Integer idTicket)
			throws PersistenceException {
		dataBaseTicket.resolveTicket(codigoMateria, idTicket);
	}

	@Override
	public void closeTicket(String codigoMateria, Integer idTicket)
			throws PersistenceException {
		dataBaseTicket.closeTicket(codigoMateria, idTicket);
	}


	/*
	 * FIN GUI 
	 */

	

}
