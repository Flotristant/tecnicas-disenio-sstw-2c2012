package services;

import services.exceptions.InvalidPortFormatException;
import services.exceptions.InvalidUserFormatException;

public abstract class ProtocolMail {
	protected String user, pass, port, host;
	
	public ProtocolMail(String user, String pass, String port, String host) throws InvalidPortFormatException, InvalidUserFormatException {
		this.validatePort(port);
		this.validateUser(user);
		this.user= user;
		this.pass= pass;
		this.port = port;
		this.host= host;
	}
	
	public String getUser() {
		return user;
	}
	
	protected void setUser(String user) {
		this.user = user;
	}
	
	public String getPass() {
		return pass;
	}
	
	protected void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getPort() {
		return port;
	}
	
	protected void setPort(String port) {
		this.port = port;
	}
	
	public String getHost() {
		return host;
	}
	
	protected void setHost(String host) {
		this.host = host;
	}
	
	private void validatePort(String port) throws InvalidPortFormatException {
		int port_number = 0;
		try {
			port_number = Integer.parseInt(port);
		}
		catch (NumberFormatException e) {
			throw new InvalidPortFormatException();		
		}
		if ( port_number <= 0) {
			throw new InvalidPortFormatException();	
		}
	}
	
	private void validateUser(String user) throws InvalidUserFormatException {
		if (!(user.contains("@"))) {
			throw new InvalidUserFormatException();
		}
	}
}
