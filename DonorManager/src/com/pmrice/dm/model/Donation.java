package com.pmrice.dm.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pmrice.dm.util.Util;

public class Donation implements Serializable {

	private static final long serialVersionUID = 3647899658839045729L;
	
	private int id;
	private Date date = Util.today();
	private String description = "";
	private String amount = "0.00";
	private String note = "";
	
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
		
	public static Donation get(int id) throws SQLException {
		Connection con = Util.getConnection();
		ResultSet rs = con.createStatement().executeQuery("SELECT * FROM donor WHERE id = '" + id + "';");
		Donation donation = new Donation(
				rs.getInt("id"),
				rs.getDate("date"), 
				rs.getString("description"), 
				rs.getString("amount"), 
				rs.getString("note"));
		return donation;
	}
	
	public static boolean remove(int id) throws SQLException {
		Connection con = Util.getConnection();
		return con.createStatement().execute("REMOVE FROM donation WHERE id = '" + id + "';");
	}
	
	public static boolean update(Donation donation) throws SQLException {
		Connection con = Util.getConnection();
		StringBuilder b = new StringBuilder("UPDATE user ")
		.append("SET date = ").append(donation.getDate()).append(",")
		.append("SET description = '").append(donation.getDescription()).append("',")
		.append("SET amount = '").append(donation.getAmount()).append("',")
		.append("SET note = '").append(donation.getNote()).append("',")
		.append("WHERE id = ").append(donation.getId()).append(";");
		return con.createStatement().execute(b.toString());
	}
	
	public static boolean add(Donation donation) throws SQLException {
		Connection con = Util.getConnection();
		StringBuilder b = new StringBuilder("INSERT INTO donor (id, date, description, amount, note) VALUES(")
		.append(donation.getId()).append(",")
		.append(donation.getDate()).append(",")
		.append("'").append(donation.getDescription()).append("',")
		.append("'").append(donation.getAmount()).append("',")
		.append("'").append(donation.getNote()).append("');");
		return con.createStatement().execute(b.toString());
	}


}
