package com.pmrice.dm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.pmrice.dm.model.Donor;
import com.pmrice.dm.model.User;
import com.pmrice.dm.util.Currency;
import com.pmrice.dm.util.Util;

class Tests {

	@Test
	void currencyTest() {
		assertEquals("56.99", Currency.store("$56.99"));
		assertEquals("4000.80", Currency.store("4,000.8"));
		assertEquals("355.00", Currency.store("355"));
		assertEquals("355.00", Currency.store("355."));
		assertEquals("65778.03", Currency.store("$ 65,778.03"));
		assertEquals("$123,456.78",Currency.getDisplay("123456.78", true));
		assertEquals("$23,456.78",Currency.getDisplay("23456.78", true));
		assertEquals("$3,456.78",Currency.getDisplay("3456.78", true));
		assertEquals("$456.78",Currency.getDisplay("456.78", true));
		assertEquals("$56.78",Currency.getDisplay("56.78", true));
		assertEquals("$6.78",Currency.getDisplay("6.78", true));
		assertEquals("$0.78",Currency.getDisplay("0.78", true));
		BigDecimal bd = new BigDecimal(45.678);
		assertEquals("45.67", new Currency(bd).toString());
	}
	
	@Test
	void utilTest() {
		assertNotNull(Util.getConnection());
	}
	
	@Test
	void donorTest() {
		try {
			Donor donor = new Donor();
			donor.setName("Peter");
			donor = Donor.add(donor);
			donor.setEmail("");
			assertTrue(Donor.update(donor));
			assertEquals("abc@xyz.com", donor.getEmail());
			assertTrue(Donor.remove(donor.getId()));
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	@Test
	void userTest() {
		try {
			User user = new User("admin","admin",true);
			assertTrue(User.add(user));
			user.setPassword("Admin");
			assertTrue(User.update(user));
			user = User.get("admin");
			assertEquals("Admin", user.getPassword());
			assertTrue(User.remove("admin"));
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}

}
