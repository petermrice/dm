package com.pmrice.dm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.GregorianCalendar;

public class Util {

	
	public static String today() {
		java.sql.Date date = new java.sql.Date(GregorianCalendar.getInstance().getTimeInMillis());
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		String dt = df.format(date);
		String[] parts = dt.split("/");
		return parts[0] + "/" + parts[1] + "/20" + parts[2];
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
	
	public static void createDatabase() throws SQLException {
		getConnection().createStatement().execute("DROP DATABASE IF EXISTS dm;");
		getConnection().createStatement().execute("CREATE DATABASE dm DEFAULT CHARACTER SET utf8;");
	}
	
	public static void dropDatabase() throws SQLException {
		getConnection().createStatement().execute("DROP DATABASE IF EXISTS dm;");
	}
	
	/**
	 * Change a date string 'mm/dd/yyyy' to 'yyy-mm-dd' for storage.
	 * 
	 * @param s
	 * @return
	 */
	public static String displayToStorage(String s) {
		String[] parts = s.split("/");
		if (parts.length != 3) {
			System.out.println("Bad date format: " + s);
			return "";
		}
		return parts[2] + "-" + parts[0] + "-" + parts[1];
	}
	
	public static String storageToDisplay(String s) {
		String[] parts = s.split("-");
		if (parts.length != 3) {
			System.out.println("Bad date format: " + s);
			return "";
		}
		return parts[1] + "/" + parts[2] + "/" + parts[0];
	}
	
	/**
	 * Looking for a string in the form mm/dd/yyyy
	 * @param in
	 * @return
	 */
	public static boolean isDateValid(String s) {
		if (s == null) return false;
		String[] parts = s.split("/");
		if (parts.length != 3 || parts[0].length() != 2 || parts[1].length() != 2 | parts[2].length() != 4) {
			System.out.println("Bad date format: " + s);
			return false;
		}
		return true;
	}
}
