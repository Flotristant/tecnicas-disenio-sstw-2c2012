package services;

public abstract class ProtocolMail {
	protected String user, pass, port, host;
	public ProtocolMail(String user, String pass, String port, String host) {
		this.user= user;
		this.pass= pass;
		this.port = port;
		this.host= host;
	}
}
