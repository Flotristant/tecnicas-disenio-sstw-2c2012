package applicationServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTPAdapter implements ITPAdapter {
	
	public DBTPAdapter(String dbname, boolean cleardb) throws Exception {
		Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db"); 
        Statement stat = conn.createStatement(); 
        stat.executeUpdate("CREATE TABLE IF NOT EXISTS TP (Subject, StudentNr, TPNr)");
        stat.close();
        conn.close();

	}
	
	@Override
	public void setTPDelivered(int subject, int studentnr, int TPnr)
			throws Exception {
		 Class.forName("org.sqlite.JDBC");
         Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db"); 
         Statement stat = conn.createStatement(); 
         stat.executeUpdate(String.format("INSERT INTO TP (Subject, StudentNr, TPNr) VALUES (%d, %d, %d)", subject, studentnr, TPnr));
         stat.close();
         conn.close(); 
	}

	@Override
	public boolean isTPDelivered(int subject, int studentnr, int TPnr)
			throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db"); 
		Statement stat = conn.createStatement(); 
		ResultSet rs = stat.executeQuery(String.format("SELECT * FROM TP WHERE Subject=%d and StudentNr=%d and TPNr=%d", subject, studentnr, TPnr));
		
		int count = 0;
		rs.next();
		while (!rs.isAfterLast()) {
			count++;
			rs.next();
		}
		rs.close();
		stat.close();
        conn.close(); 
		return count == 1;
	}
}
