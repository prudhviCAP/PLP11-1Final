package com.cg.insurance.test;

import java.sql.Connection;

import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cg.insurance.dao.IInsuredDao;
import com.cg.insurance.dao.InsuredDaoImpl;
import com.cg.insurance.util.DBConnection;

public class DBConnectionTest {

	static IInsuredDao iInsuredDao;
	static Connection dbCon;

	@BeforeAll
	public static void initialise() {
		iInsuredDao = new InsuredDaoImpl();
		dbCon = null;
	}

	@BeforeEach
	public void beforeEachTest() {
		System.out.println("----Starting DBConnection Test Case----\n");
	}

	@Test
	public void test() throws Exception {
		Connection dbCon = DBConnection.getConnection();
		Assert.assertNotNull(dbCon);
	}

	@After
	public void afterEachTest() {
		System.out.println("----End of DBConnection Test Case----\n");
	}

	@AfterAll
	public static void destroy() {
		System.out.println("\t----End of Tests----");
		iInsuredDao = null;
		dbCon = null;
	}
}
