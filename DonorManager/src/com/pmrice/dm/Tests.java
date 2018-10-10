package com.pmrice.dm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.pmrice.dm.model.Donation;
import com.pmrice.dm.model.Donor;
import com.pmrice.dm.model.Pledge;
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
		assertEquals("456.78",Currency.getDisplay("456.78", false));
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
	void donationTest() {
		// create donor for donation
		Donor donor = new Donor();
		donor.setName("Peter");
		donor = Donor.add(donor);
		// create and test new Donation
		Donation don = new Donation();
		don.setDonorId(donor.getId());
		don = Donation.add(don);
		assertNotNull(don);
		Date today = Util.today();
		don.setDate(today);
		assertTrue(Donation.update(don));
		Donation don2 = Donation.get(don.getId());
		assertNotNull(don2);
		assertEquals(don.getId(), don2.getId());
		assertTrue(Donation.remove(don.getId()));
		// remove donor
		assertTrue(Donor.remove(donor.getId()));
	}
	
	@Test
	void pledgeTest() {
		// create donor for pledge
		Donor donor = new Donor();
		donor.setName("Peter");
		donor = Donor.add(donor);
		// create and test new Pledge
		Pledge pledge = new Pledge();
		pledge.setDonorId(donor.getId());
		pledge = Pledge.add(pledge);
		assertNotNull(pledge);
		Date today = Util.today();
		pledge.setBeginDate(today);
		pledge.setFulfilled(true);
		assertTrue(Pledge.update(pledge));
		Pledge pledge2 = Pledge.get(pledge.getId());
		assertNotNull(pledge2);
		assertEquals(pledge.getId(), pledge2.getId());
		assertTrue(pledge.isFulfilled());
		assertTrue(Pledge.remove(pledge.getId()));
		// remove donor
		assertTrue(Donor.remove(donor.getId()));
	}
	
	@Test
	void donorTest() {
		Donor donor = new Donor();
		donor.setName("Peter");
		donor = Donor.add(donor);
		assertNotNull(donor);
		donor.setEmail("abc@xyz.com");
		Donor.update(donor);
		donor = Donor.get(donor.getId());
		assertEquals("abc@xyz.com", donor.getEmail());
		assertTrue(Donor.remove(donor.getId()));
	}
	
	@Test
	void userTest() {
		User user = new User("admin","admin",true);
		assertTrue(User.add(user));
		user.setPassword("Admin");
		User.update(user);
		user = User.get("admin");
		assertEquals("Admin", user.getPassword());
		assertTrue(User.remove("admin"));
		
	}

}
