package applicationServices;

import java.sql.ResultSet;
import java.util.*;

import services.SQLiteAccessor;
import services.SQLiteAccessor.QueryHandler;

public class DBSignupAdapter implements ISignupAdapter {
	private SQLiteAccessor db;
	
	public DBSignupAdapter(String dbname, boolean cleardb) throws Exception {
		db = new SQLiteAccessor(dbname);
		if (cleardb) {
			db.execute("DROP TABLE IF EXISTS Signup;");
		}
		db.execute("CREATE TABLE IF NOT EXISTS Signup (Subject int, StudentNr int);");
	}
	
	public List<Integer> getSignupsForSubject (int subject) throws Exception {
		ArrayList<Integer> res = new ArrayList<Integer>();
		QueryHandler qry = db.query(String.format("SELECT StudentNr from Signup WHERE Subject=%d", subject));
		ResultSet rset = qry.getResultSet();
		rset.next();
		while (!rset.isAfterLast()) {
			res.add(rset.getInt(1));
			rset.next();
		}
		qry.close();
		return res;
	}
	
	public void setSignup(int subject, int studentnr) throws Exception {
		db.update(String.format("INSERT INTO Signup (Subject, StudentNr) VALUES (%d, %d)", subject, studentnr));
	}
	
	public void close() throws Exception {
		db.close();
	}
}
