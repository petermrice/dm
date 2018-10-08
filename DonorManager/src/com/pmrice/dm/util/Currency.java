package com.pmrice.dm.util;

import java.math.BigDecimal;
import java.util.List;

/**
 * Currency is stored as a string, no commas, no currency symbol, with two cents digits showing
	   the max value allowed is 999999.99
	   
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
	
	public static String getDisplay(String store, boolean showCurrencySymbol) {
		if (store == null || store.length() == 0) return "0.00";
		if (store.length() < 4) return "0" + store;
		String cents = store.substring(store.length() - 3); // less than 3 chars is an error.
		store = store.substring(0, store.length() - 3);
		String hundreds = "";
		String thousands = "";
		if (store.length() > 3) {
			thousands = store.substring(0, store.length() - 3);
			hundreds = store.substring(store.length() - 3);
		} else {
			hundreds = store;
		}
		StringBuilder sb = new StringBuilder();
		if (showCurrencySymbol) sb.append("$");
		if (thousands.length() > 0) sb.append(thousands).append(",").append(hundreds).append(cents);
		else if (hundreds.length() > 0) sb.append(hundreds).append(cents);
		return sb.toString();
	}
	
	public static String store (String display) {
		if (display.charAt(0) == '$') display = display.substring(1);
		int dec = -1;
		if ((dec = display.indexOf(",")) > 0) display = display.substring(0,  dec) + display.substring(dec + 1);
		dec = display.indexOf(".");
		if (dec == -1) display = display + ".00";
		else if (dec == (display.length() - 1)) display = display + "00";
		else if (dec == (display.length() - 2)) display = display + "0";
		if (display.length() < 4) display = "0" + display;
		return display.trim();
	}
	
	public static void main(String[] args) {
		System.out.println(store("$56.99"));
		System.out.println(store("4,000.8"));
		System.out.println(store("355"));
		System.out.println(store("355."));
		System.out.println(store("$ 65,778.03"));
		System.out.println(getDisplay("123456.78", true));
		System.out.println(getDisplay("23456.78", true));
		System.out.println(getDisplay("3456.78", true));
		System.out.println(getDisplay("456.78", true));
		System.out.println(getDisplay("56.78", true));
		System.out.println(getDisplay("6.78", true));
		System.out.println(getDisplay("0.78", true));
		BigDecimal bd = new BigDecimal(45.678);
		System.out.println(new Currency(bd).toString());
	}

}
