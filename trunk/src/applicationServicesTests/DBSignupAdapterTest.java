package applicationServicesTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import applicationServices.DBSignupAdapter;


public class DBSignupAdapterTest {

	@Test
	public void test() {
		DBSignupAdapter db = null;
		try {
			db = new DBSignupAdapter("testdb.sqlite", true);
			db.setSignup(100, 1010);
			List<Integer> res = db.getSignupsForSubject(100);
			if (res.size() != 1) {
				fail("DB returns wrong query size: "+res.size());
			}
			if (res.get(0) != 1010) {
				fail("DB returns wrong result");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			try {
				if (db!=null) db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
		@Test
		public void test2() {
			DBSignupAdapter db = null;
			try {
				db = new DBSignupAdapter("testdb.sqlite", true);
				db.setSignup(7500, 1010);
				db.setSignup(7500, 1011);
				List<Integer> res = db.getSignupsForSubject(7500);
				if (res.size() != 2) {
					fail("DB returns wrong query size: "+res.size());
				}
				if (res.get(0) != 1010) {
					fail("DB returns wrong result");
				}
				if (res.get(1) != 1011) {
					fail("DB returns wrong result");
				}
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			} finally {
				try {
					if (db!=null) db.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

}
