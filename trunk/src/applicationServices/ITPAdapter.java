package applicationServices;

public interface ITPAdapter {
	public void setTPDelivered(int subject, int studentnr, int TPnr) throws Exception;
	public boolean isTPDelivered(int subject, int studentnr, int TPnr) throws Exception;
}
