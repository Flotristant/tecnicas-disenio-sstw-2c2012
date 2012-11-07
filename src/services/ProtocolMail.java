package services;

public abstract class ProtocolMail {
	protected String user, pass, port, host;
	
	public ProtocolMail(String user, String pass, String port, String host) {
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
	
}
