package com.pmrice.dm.util;

import java.math.BigDecimal;
import java.util.List;

/**
 * Currency is stored as a string, no commas, no currency symbol. The value is a whole
 * number equal to the dollar value X 100.
 * The max value allowed is 999,999,999.99.
	   
 * @author peterrice
 *
 */
public class Currency {
	
	private String store;
	
	public Currency() {
		store = "0.00";
	}
	
	public Currency(String store) {
		this.store = store;
	}
	
	public Currency(BigDecimal bd) {
		store = normalize(bd).toString();;
	}
	
	public String getStore() {
		return store;
	}
	
	public String display(boolean showCurrencySymbol) {
		return getDisplay(store, showCurrencySymbol);
	}
	
	public void setStoreFromDisplay(String display) {
		store = store(display);
	}
	
	public Currency add(Currency addend) {
		BigDecimal ad = new BigDecimal(addend.store);
		return new Currency(new BigDecimal(store).multiply(ad));
	}
	
	/**
	 * Add up a list of Currency values
	 * 
	 * @param list
	 * @return
	 */
	public Currency total(List<Currency> list) {
		if (list.size() == 0) return new Currency();
		BigDecimal sum = new BigDecimal(list.get(0).store);
		for (int i = 1; i < list.size(); i++)
			sum = sum.add(new BigDecimal(list.get(i).store));
		return new Currency(sum);
	}
	
	/*
	 * remove all fraction digits after the second.
	 */
	private BigDecimal normalize(BigDecimal in) {
		BigDecimal hundred = new BigDecimal(100);
		BigDecimal hundredth = new BigDecimal(0.01);
		BigDecimal b = in.divideToIntegralValue(hundredth);
		return b.divide(hundred);
	}
	
	@Override
	public String toString() {
		return store;
	}
	
	/**
	 * Validate a user entry. Using , to separate thousands is OK,
	 * but it is also OK to misuse them. When storing, they are removed anyway.
	 * 
	 * @param in
	 * @return
	 */
	public static boolean validate(String in) {
		char[] chars = in.toCharArray();
		boolean foundDecimal = false;
		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			if (ch == '.') {
				if (foundDecimal) return false; // one too many!
				foundDecimal = true;
			} else if (ch == '$') {
				if (i != 0) return false;
			} else {
				if (Character.isDigit(ch) || ch == ',') continue;
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Display = store X 100, with the decimal point and commas added to separate blocks of three digits.
	 * @param store
	 * @param showCurrencySymbol
	 * @return
	 */
	public static String getDisplay(String store, boolean showCurrencySymbol) {
		boolean b = showCurrencySymbol;
		if (store == null || store.length() == 0) return "0.00";
		if (store.length() == 1) return b ? ("$0.0" + store) : ("0.0" + store); 
		if (store.length() == 2) return b ? ("$0."  + store) : ("0."  + store); 
		String thousands = "";
		String millions = "";
		String dollars = store.substring(0, store.length() - 2);
		String cents = store.substring(store.length() - 2, store.length());
		if (dollars.length() > 3) {
			thousands = dollars.substring(0, dollars.length() - 3);
			dollars = dollars.substring(dollars.length() - 3, dollars.length());
		}
		if (thousands.length() > 3) {
			millions = thousands.substring(0, thousands.length() - 3);
			thousands = thousands.substring(thousands.length() - 3, thousands.length());
		}
		if (thousands.length() == 0) return (b ? "$" : "") + dollars + "." + cents;
		if (millions.length() == 0) return (b ? "$" : "") + thousands + "," + dollars + "." + cents;
		return (b ? "$" : "") + millions + "," + thousands + "," + dollars + "." + cents;
	}
	
	public static String store (String display) {
		// remove commas, spaces, etc. (not periods)
		String dollars = "";
		String cents = "";
		int c = display.indexOf('.');
		if (c == -1) {
			dollars = display;
		} else {
			dollars = display.substring(0, c);
			cents = display.substring(c + 1);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dollars.length(); i++) {
			if (Character.isDigit(dollars.charAt(i))) sb.append(dollars.charAt(i));
		}
		dollars = sb.toString();
		sb = new StringBuilder();
		for (int i = 0; i < cents.length(); i++) {
			if (Character.isDigit(cents.charAt(i))) sb.append(cents.charAt(i));
		}
		cents = sb.toString();
		if (cents.length() == 0) cents = "00";
		if (cents.length() == 1) cents = cents + "0";
		else cents = cents.substring(0,2);
		return dollars + cents;
	}
	
	public static void main(String[] args) {
		System.out.println(store("$56.99"));
		System.out.println(store("4,000.8"));
		System.out.println(store("4,56,8.00"));
		System.out.println(store("355"));
		System.out.println(store("355."));
		System.out.println(store("$ 65,778.03"));
		System.out.println(getDisplay("55612345678", true));
		System.out.println(getDisplay("5612345678", true));
		System.out.println(getDisplay("512345678", true));
		System.out.println(getDisplay("12345678", true));
		System.out.println(getDisplay("2345678", true));
		System.out.println(getDisplay("345678", true));
		System.out.println(getDisplay("45678", true));
		System.out.println(getDisplay("5678", true));
		System.out.println(getDisplay("678", true));
		System.out.println(getDisplay("78", true));
		System.out.println(getDisplay("8", true));
		System.out.println(getDisplay("55435699",false));
		BigDecimal bd = new BigDecimal(45.678);
		System.out.println(validate("$56,988.01"));
		System.out.println(new Currency(bd).toString());
		System.out.println(validate("$56.78"));
		System.out.println(validate("56.8"));
		System.out.println(validate("$5,6.78"));
		
	}

}
