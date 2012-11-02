package applicationServices;

import java.util.List;

public interface SignupAdapter {
	public List<Integer> getSignupsForSubject (int subject) throws Exception;
	public void setSignup(int subject, int studentnr) throws Exception;
	public void close() throws Exception;
}
