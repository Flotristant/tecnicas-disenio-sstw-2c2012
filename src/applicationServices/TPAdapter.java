package applicationServices;

public interface TPAdapter {
	public void setTPDelivered(int subject, int studentnr, int TPnr) throws Exception;
	public boolean isTPDelivered(int subject, int studentnr, int TPnr) throws Exception;
	public void close() throws Exception;
}
