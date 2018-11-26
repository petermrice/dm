package com.pmrice.dm.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Util {
	
	public static String today() {
		java.sql.Date date = new java.sql.Date(GregorianCalendar.getInstance().getTimeInMillis());
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		String dt = df.format(date);
		String[] parts = dt.split("/");
		return parts[0] + "/" + parts[1] + "/20" + parts[2];
	}
	
	public static int year() {
		Calendar calendar = GregorianCalendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
			
	/**
	 * Change a date string 'mm/dd/yyyy' to 'yyyy-mm-dd' for storage.
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
		String day = parts[1];
		if (day.length() == 1) day = "0" + day;
		String month= parts[0];
		if (month.length() == 1) month = "0" + month;
		String year= parts[2];
		if (year.length() == 2) year = "20" + year;
		return year + "-" + month + "-" + day;
	}
	
	public static String storageToDisplay(String s) {
		String[] parts = s.split("-");
		if (parts.length != 3) {
			System.out.println("Bad date format: " + s);
			return "";
		}
		String day = parts[2];
		String month = parts[1];
		String year = parts[0];
		if (month.charAt(0) == '0') month = month.substring(1);
		if (day.charAt(0) == '0') day = day.substring(1);
		return month + "/" + day + "/" + year;
	}
	
	/**
	 * Looking for a string in the form mm/dd/yyyy
	 * @param in
	 * @return
	 */
	public static boolean isDateValid(String s) {
		if (s == null) return false;
		String[] parts = s.split("/");
		try {
			int day = Integer.parseInt(parts[1]);
			int month = Integer.parseInt(parts[0]);
			int year = Integer.parseInt(parts[2]);
			if (day < 1 || day > 31) return false;
			if (month < 1 || month > 12) return false;
			if (year < 2000 || year > 3000) return false;
		} catch (NumberFormatException e) {
			System.out.println("Util.isDateValid: Error parsing a number: " + s);
		}
		return true;
	}
	
	private static String[] monthname = new String[] {"January","February","March","April","May","June","July","August","September","October","November","December"};
	
	/**
	 * Return the month name and year from a date string in the form mm/dd/yyyy
	 * @param month name
	 * @return
	 */
	public static String getMonthName(String mm) {
		return monthname[Integer.parseInt(mm) - 1];
	}
	
	/**
	 * Assume date has the form mm/dd/yyyy
	 * @param date
	 * @return
	 */
	public static String getMonthBegin(String date) {
		String mm = date.substring(0, date.indexOf('/'));
		String yyyy = date.substring(date.lastIndexOf('/') + 1);
		return mm + "/01/" + yyyy; 
	}
	
	/**
	 * Assume date has the form mm/dd/yyyy
	 * @param date
	 * @return
	 */
	public static String getMonthEnd(String date) {
		String mm = date.substring(0, date.indexOf('/'));
		String yyyy = date.substring(date.lastIndexOf('/') + 1);
		String dd = "";
		switch (mm) {
		case "01":
		case "03":
		case "05":
		case "07":
		case "08":
		case "10":
		case "12":
			dd = "31";
			break;
		case "04":
		case "06":
		case "09":
		case "11":
			dd = "30";
			break;
		case "02":
			if (Integer.parseInt(yyyy) % 4 == 0) dd = "29";
			else dd = "28";
			break;
		default:
			dd = "error";
		}
		return mm + "/" + dd + "/" + yyyy;
	}
	
}
