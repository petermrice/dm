package com.pmrice.dm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		assertEquals("5699", Currency.store("$56.99"));
		assertEquals("400080", Currency.store("4,000.8"));
		assertEquals("35500", Currency.store("355"));
		assertEquals("35500", Currency.store("355."));
		assertEquals("6577803", Currency.store("$ 65,778.03"));
		assertEquals("$123,456.78",Currency.getDisplay("12345678", true));
		assertEquals("$23,456.78",Currency.getDisplay("2345678", true));
		assertEquals("$3,456.78",Currency.getDisplay("345678", true));
		assertEquals("456.78",Currency.getDisplay("45678", false));
		assertEquals("$56.78",Currency.getDisplay("5678", true));
		assertEquals("$6.78",Currency.getDisplay("678", true));
		assertEquals("$0.78",Currency.getDisplay("078", true));
		long bd = 45678L;
		assertEquals("45678", new Currency(bd).toString());
	}
	
	@Test
	void utilTest() {
		assertNotNull(Util.getConnection());
	}
	
	@Test
	void donationTest() {
		// create donor for donation
		Donor donor = new Donor();
		donor.setName("Tester");
		donor = Donor.add(donor);
		// create and test new Donation
		Donation don = new Donation();
		don.setDonorId(donor.getId());
		don.setDate("10/12/2018");
		don.setAmount("$3,500.00");
		don.setDescription("test");
		don = Donation.add(don);
		assertNotNull(don);
		don.setAmount("$3,500.44");
		don.setDescription("test");
		assertTrue(Donation.update(don));
		Donation don2 = Donation.get(don.getId());
		assertNotNull(don2);
		assertEquals(don.getId(), don2.getId());
		assertEquals("3,500.44", don2.getAmount());
		assertEquals("10/12/2018", don2.getDate());
		assertEquals("test", don2.getDescription());
		assertTrue(Donation.remove(don.getId()));
		// remove donor
		assertTrue(Donor.remove(donor.getId()));
	}
	
	@Test
	void pledgeTest() {
		// create donor for pledge
		Donor donor = new Donor();
		donor.setName("Tester");
		donor = Donor.add(donor);
		// create and test new Pledge
		Pledge pledge = new Pledge();
		pledge.setDonorId(donor.getId());
		pledge = Pledge.add(pledge);// add
		assertNotNull(pledge);
		String today = Util.today();
		pledge.setBeginDate(today);
		pledge.setFulfilled(true);
		assertTrue(Pledge.update(pledge));// update
		Pledge pledge2 = Pledge.get(pledge.getId());// get
		assertNotNull(pledge2);
		assertEquals(pledge.getId(), pledge2.getId());
		assertTrue(pledge.isFulfilled());
		assertTrue(Pledge.remove(pledge.getId()));// remove
		// remove donor
		assertTrue(Donor.remove(donor.getId()));
	}
	
	@Test
	void donorTest() {
		Donor donor = new Donor();
		donor.setName("Tester");
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
