package services;

import java.sql.*;

public class SQLiteAccessor {
	private Connection connection;
	public SQLiteAccessor(String dbname) throws Exception {

		System.setProperty("sqlite.purejava", "true");
		Class.forName("org.sqlite.JDBC");

		this.connection = DriverManager.getConnection("jdbc:sqlite:"+dbname);
	}
	
	public void execute(String sql) throws Exception {
		Statement stat = this.connection.createStatement();
		stat.setQueryTimeout(5);
		stat.execute(sql);
		stat.close();
	}
	
	public int update(String sql) throws Exception {
		Statement stat = this.connection.createStatement();
		stat.setQueryTimeout(5);
		int res = stat.executeUpdate(sql);
		stat.close();
		return res;
	}
	
	public class QueryHandler {
		private Statement stat;
		private ResultSet resultset;
		protected QueryHandler(Statement stat, ResultSet res) {
			this.stat = stat;
			this.resultset = res;
		}
		public ResultSet getResultSet() {
			return this.resultset;
		}
		public void close() {
			try {
				this.resultset.close();
				this.stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public QueryHandler query(String sql) throws Exception {
		Statement stat = this.connection.createStatement();
		stat.setQueryTimeout(5);
		ResultSet result = stat.executeQuery(sql);
		return new QueryHandler(stat, result);
	}
	
	public void close() throws Exception {
		this.connection.close();
	}
}