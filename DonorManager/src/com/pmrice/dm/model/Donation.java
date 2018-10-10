package com.pmrice.dm.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

import com.pmrice.dm.util.Util;

public class Donation implements Serializable {

	private static final long serialVersionUID = 3647899658839045729L;
	
	private int id;
	private int donor_id;
	private Date date = Util.today();
	private String description = "";
	private String amount = "0.00";
	private String note = "";
	
	public Donation() {
		
	}
	
	public Donation(int id, Date date, String description, String amount, String note) {
		super();
		this.id = id;
		this.date = date;
		this.description = description;
		this.amount = amount;
		this.note = note;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getDonorId() {
		return donor_id;
	}
	public void setDonorId(int id) {
		this.donor_id = id;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
		
	public static Donation get(int id)  {
		try {
			Connection con = Util.getConnection();
			String sql = "SELECT * FROM dm.donation WHERE id = '" + id + "';";
			ResultSet rs = con.createStatement().executeQuery(sql);
			if (!rs.next()) return null;
			Donation donation = new Donation(
				rs.getInt("id"),
				rs.getDate("date"), 
				rs.getString("description"), 
				rs.getString("amount"), 
				rs.getString("note"));
			return donation;
		} catch (Exception e) {
			System.out.println("Error getting a Donation.");
			return null;
		}
	}
	
	public static boolean remove(int id) {
		try {
			Connection con = Util.getConnection();
			String sql = "DELETE FROM dm.donation WHERE id = '" + id + "';";
			con.createStatement().execute(sql);
			return true;
		} catch (Exception e) {
			System.out.println("Error removing Donation: e");
			return false;
		}
	}
	
	public static boolean update(Donation donation) {
		try {
			Connection con = Util.getConnection();
			StringBuilder b = new StringBuilder("UPDATE dm.donation ")
				.append("SET date = '").append(donation.getDate()).append("',")
				.append(" description = '").append(donation.getDescription()).append("',")
				.append(" amount = '").append(donation.getAmount()).append("',")
				.append(" note = '").append(donation.getNote()).append("' ")
				.append("WHERE id = ").append(donation.getId()).append(";");
			String sql = b.toString();
			con.createStatement().execute(sql);
			return true;
		} catch (Exception e) {
			System.out.println("Error updating Donation: e");
			return false;
		}
	}
	
	public static Donation add(Donation donation) {
		try {
			Connection con = Util.getConnection();
			StringBuilder b = new StringBuilder("INSERT INTO dm.donation (id, date, description, amount, note) VALUES(")
				.append(donation.getId()).append(",")
				.append("'").append(donation.getDate()).append("',")
				.append("'").append(donation.getDescription()).append("',")
				.append("'").append(donation.getAmount()).append("',")
				.append("'").append(donation.getNote()).append("');");
			String sql = b.toString();
			con.createStatement().execute(sql);
			ResultSet rs = con.createStatement().executeQuery("SELECT LAST_INSERT_ID()");
			if (!rs.next()) return null;
			donation.setId(rs.getInt(1));
			return donation;
		} catch (Exception e) {
			System.out.println("Error adding Donation: e");
			return null;
		}
	}


}
