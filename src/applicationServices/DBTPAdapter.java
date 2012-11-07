package applicationServices;

import java.sql.ResultSet;

import services.SQLiteAccessor;
import services.SQLiteAccessor.QueryHandler;

public class DBTPAdapter implements TPAdapter {
	private SQLiteAccessor db;
	
	public DBTPAdapter(String dbname, boolean cleardb) throws Exception {
		db = new SQLiteAccessor(dbname);
		if (cleardb) {
			db.execute("DROP TABLE IF EXISTS TP;");
		}
		db.execute("CREATE TABLE IF NOT EXISTS TP (Subject int, StudentNr int, TPNr int);");
	}
	
	public void close() throws Exception {
		db.close();
	}

	@Override
	public void setTPDelivered(int subject, int studentnr, int TPnr)
			throws Exception {
		db.update(String.format("INSERT INTO TP (Subject, StudentNr, TPNr) VALUES (%d, %d, %d)", subject, studentnr, TPnr));
	}

	@Override
	public boolean isTPDelivered(int subject, int studentnr, int TPnr)
			throws Exception {
		String sql = String.format("SELECT * FROM TP WHERE Subject=%d and StudentNr=%d and TPNr=%d", subject, studentnr, TPnr);
		System.out.println(sql);
		QueryHandler q = db.query(sql);
		ResultSet rs = q.getResultSet();
		int count = 0;
		rs.next();
		while (!rs.isAfterLast()) {
			count++;
			rs.next();
		}
		return count == 1;
	}
}
