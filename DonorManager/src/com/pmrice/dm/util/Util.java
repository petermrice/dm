package com.pmrice.dm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.GregorianCalendar;

public class Util {

	
	public static java.sql.Date today() {
		return new java.sql.Date(GregorianCalendar.getInstance().getTimeInMillis());
	}
	
	public static Connection getConnection() {
		try {
			new com.mysql.jdbc.Driver();
			return DriverManager.getConnection("jdbc:mysql://localhost/dm");
		} catch (SQLException e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
	public static boolean createDatabase() throws SQLException {
		if (!getConnection().createStatement().execute("DROP DATABASE IF EXISTS dm;")) return false;
		return getConnection().createStatement().execute("CREATE DATABASE dm DEFAULT CHARACTER SET utf8;");
	}
	
	public static boolean dropDatabase() throws SQLException {
		return getConnection().createStatement().execute("DROP DATABASE IF EXISTS dm;");
	}
}
