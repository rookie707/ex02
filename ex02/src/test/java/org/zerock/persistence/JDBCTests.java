package org.zerock.persistence;


import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCTests {
	private static final Logger logger = LoggerFactory.getLogger(JDBCTests.class);
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection() {
		try(Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ex02?serverTimezone=Asia/Seoul",
				"root",
				"1234"
				)){
			logger.info("con");
		}catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
