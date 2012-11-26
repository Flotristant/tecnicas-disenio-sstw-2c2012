package model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.listeners.IResponseMailEventListener;

import persistence.IMateriaPersistence;
import persistence.exceptions.PersistenceException;

public abstract class Rule implements IRule {

	private ArrayList<ActionRule> collectionActions;
	protected String pattern;
	private String typeOfQuery;
	private String tpNumber;
	private String codigoMateria;
	private Integer padron;
	private String name;
	private String tema;
	private String ticketId;
	private Message message;
	private List<IResponseMailEventListener> listeners;
	private String codigoMateriaInSubject;
	private IMateriaPersistence materiaPersistence;
	
	public Rule(IMateriaPersistence materiaPersistence) {
		this.collectionActions = new ArrayList<ActionRule>();
		this.listeners = new ArrayList<IResponseMailEventListener>();
		this.materiaPersistence = materiaPersistence;
	}
	
	public void setTypeOfQuery(String typeOfQuery) {
		this.typeOfQuery = typeOfQuery;
	}

	public void setTpNumber(String tpNumber) {
		this.tpNumber = tpNumber;
	}

	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	public void setPadron(Integer padron) {
		this.padron = padron;
	}

	@Override
	public String getPattern() {
		return this.pattern;
	}
	
	@Override
	public void execute(Message message) throws PersistenceException {
		this.setMessage(message);
		this.setCodigoMateria(materiaPersistence.getCodigoMateria(message.getTo()));
		String subject = message.getSubject();
		Pattern pattern = Pattern.compile(this.pattern);
		Matcher matcher = pattern.matcher(subject);
		if (matcher.matches()) {
			searchComponentsInSubject(matcher);
			for (ActionRule action : this.collectionActions) {
				action.initialize(this, message);
				try {
					action.execute();
				} 
				catch (PersistenceException pe) {
					//rollback
				}
				catch (Exception e) {
					for(IResponseMailEventListener listener : this.listeners)
						listener.handleCreatedEvent(message, e.getMessage());
					break;
				}
			}
		}
	}

	protected abstract void searchComponentsInSubject(Matcher matcher);
	
	@Override
	public Iterable<ActionRule> getActions() {
		return collectionActions;
	}

	@Override
	public void addAction(ActionRule action) {
		this.collectionActions.add(action);
	}

	public String getTypeOfQuery() {
		return this.typeOfQuery;
	}

	public String getTpNumber() {
		return this.tpNumber;
	}

	public String getCodigoMateria() {
		return this.codigoMateria;
	}
	
	public void setCodigoMateriaInSubject(String codigoMateriaInSubject){
		this.codigoMateriaInSubject = codigoMateriaInSubject;
	}
	
	public String getCodigoMateriaInSubject(){
		return this.codigoMateriaInSubject;
	}

	public Integer getPadron() {
		return this.padron;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public void addSubscriber(IResponseMailEventListener listener) {
		for (ActionRule action : this.collectionActions)
			action.addSubscriber(listener);
		this.listeners.add(listener);
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
}
