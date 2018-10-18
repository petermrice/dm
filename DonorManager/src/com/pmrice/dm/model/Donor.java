package com.pmrice.dm.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pmrice.dm.util.Util;

public class Donor implements Serializable {
	
	private static final long serialVersionUID = -5814965103358184008L;

	private int id = 0;
	private String name = "";
	private String lastname = "";
	private String address1 = "", address2 = "", city = "", state = "", zip = "", country = "";
	private String telephone = "";
	private String email = "";
	private String notes = "";
	private boolean hidden = false;

	public Donor(int id, String name, String lastname, String address1, String address2, String city, String state, String country,
			String zip, String telephone, String email, String notes, boolean hidden) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.telephone = telephone;
		this.email = email;
		this.notes = notes;
		this.hidden = hidden;
	}
	
	public Donor() {
		setId(0);
		setName("New Donor");
	}
	
	public Donor(int id, String name) {
		setId(id);
		setName(name);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean value) {
		hidden = value;
	}
	
	public String toString() {
		return getName();
	}
	
	public static Donor get(int id) {
		try {
			Connection con = Util.getConnection();
			String sql = "SELECT * FROM dm.donor WHERE id = '" + id + "';";
			ResultSet rs = con.createStatement().executeQuery(sql);
			if (!rs.next()) return null; // no value returned
			Donor donor = new Donor(
					rs.getInt("id"),
					rs.getString("name"), 
					rs.getString("lastname"), 
					rs.getString("address1"), 
					rs.getString("address2"), 
					rs.getString("city"), 
					rs.getString("state"), 
					rs.getString("zip"), 
					rs.getString("country"),
					rs.getString("telephone"), 
					rs.getString("email"), 
					rs.getString("notes"), 
					rs.getBoolean("hidden"));
			return donor;
		} catch (Exception e) {
			System.out.println("Error getting a Donor.");
			return null;
		}
	}
	
	public static boolean remove(int id) {
		try {
			Connection con = Util.getConnection();
			String sql = "DELETE FROM dm.donor WHERE id = '" + id + "';";
			con.createStatement().execute(sql);
			return true;
		} catch (Exception e) {
			System.out.println("Error getting a Donor.");
			return false;
		}
	}
	
	public static boolean update(Donor donor) {
		try {
			Connection con = Util.getConnection();
			StringBuilder b = new StringBuilder("UPDATE dm.donor SET ")
				.append(" name = '").append(donor.getName()).append("',")
				.append(" lastname = '").append(donor.getLastname()).append("',")
				.append(" address1 = '").append(donor.getAddress1()).append("',")
				.append(" address2 = '").append(donor.getAddress2()).append("',")
				.append(" city = '").append(donor.getCity()).append("',")
				.append(" state = '").append(donor.getState()).append("',")
				.append(" zip = '").append(donor.getZip()).append("',")
				.append(" country = '").append(donor.getCountry()).append("',")
				.append(" telephone = '").append(donor.getTelephone()).append("',")
				.append(" email = '").append(donor.getEmail()).append("',")
				.append(" notes = '").append(donor.getNotes()).append("',")
				.append(" hidden = ").append(donor.isHidden() ? 1 : 0)
				.append(" WHERE id = ").append(donor.getId()).append(";");
			String sql = b.toString();
			return con.createStatement().execute(sql);
		} catch (Exception e) {
			System.out.println("Error getting a Donor.");
			return false;
		}
	}
	
	public static Donor add(Donor donor) {
		try {
			Connection con = Util.getConnection();
			StringBuilder b = new StringBuilder("INSERT INTO dm.donor (id, name, lastname, address1, address2, city, state, zip, country, telephone, email, notes, hidden) VALUES(")
				.append(donor.getId()).append(",")
				.append("'").append(donor.getName()).append("',")
				.append("'").append(donor.getLastname()).append("',")
				.append("'").append(donor.getAddress1()).append("',")
				.append("'").append(donor.getAddress2()).append("',")
				.append("'").append(donor.getCity()).append("',")
				.append("'").append(donor.getState()).append("',")
				.append("'").append(donor.getZip()).append("',")
				.append("'").append(donor.getCountry()).append("',")
				.append("'").append(donor.getTelephone()).append("',")
				.append("'").append(donor.getEmail()).append("',")
				.append("'").append(donor.getNotes()).append("',")
				.append(donor.isHidden()).append(");");	
			String sql = b.toString();
			con.createStatement().execute(sql);
			ResultSet rs = con.createStatement().executeQuery("SELECT LAST_INSERT_ID()");
			if (rs.next()) donor.setId(rs.getInt(1));
			return donor;
		} catch (Exception e) {
			System.out.println("Error getting a Donor.");
			return null;
		}
	}
	
	/**
	 * Gets a list of Donor objects, but only the id and name fields are set.
	 * This is used for display of list of donors on the browser.
	 * @return
	 */
	public static List<Donor> getDonors(){
		try {
			Connection con = Util.getConnection();
			String sql = "SELECT * FROM dm.donor WHERE hidden = 0 ORDER BY lastname;";
			ResultSet rs = con.createStatement().executeQuery(sql);
			List<Donor> donors = new ArrayList<Donor>();
			Donor donor = new Donor();
			donor.setName("New Donor");
			donors.add(donor);
			while (rs.next()) {
					donor = new Donor(
					rs.getInt("id"),
					rs.getString("name"), 
					rs.getString("lastname"), 
					rs.getString("address1"), 
					rs.getString("address2"), 
					rs.getString("city"), 
					rs.getString("state"), 
					rs.getString("zip"), 
					rs.getString("country"),
					rs.getString("telephone"), 
					rs.getString("email"), 
					rs.getString("notes"), 
					rs.getBoolean("hidden"));
					donors.add(donor);
			}
			return donors;
		} catch (Exception e) {
			System.out.println("Error getting a Donor.");
			return null;
		}
	}

}
