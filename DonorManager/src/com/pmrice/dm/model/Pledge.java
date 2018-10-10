package com.pmrice.dm.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

import com.pmrice.dm.util.Util;

public class Pledge implements Serializable {

	private static final long serialVersionUID = 5800654426040039130L;
	
	private int id;
	private int donor_id;
	private String description = "";
	private String amount = "0.00";
	private Date begin_date = Util.today();
	private Date end_date = Util.today();
	private boolean fulfilled = false;
	private boolean cancelled = false;
	private String note = "";
	
	public Pledge() {
		
	}
	
	public Pledge(int id, int donor_id, String description, String amount, Date begin_date, Date end_date,
			boolean fulfilled, boolean cancelled, String note) {
		super();
		this.id = id;
		this.donor_id = donor_id;
		this.description = description;
		this.amount = amount;
		this.begin_date = begin_date;
		this.end_date = end_date;
		this.fulfilled = fulfilled;
		this.cancelled = cancelled;
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
	public void setDonorId(int donor_id) {
		this.donor_id = donor_id;
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
	
	public Date getBeginDate() {
		return begin_date;
	}
	public void setBeginDate(Date begin_date) {
		this.begin_date = begin_date;
	}
	
	public Date getEndDate() {
		return end_date;
	}
	public void setEndDate(Date end_date) {
		this.end_date = end_date;
	}
	
	public boolean isFulfilled() {
		return fulfilled;
	}
	public void setFulfilled(boolean fulfilled) {
		this.fulfilled = fulfilled;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public static Pledge get(int id)  {
		try {
			Connection con = Util.getConnection();
			String sql = "SELECT * FROM dm.pledge WHERE id = '" + id + "';";
			ResultSet rs = con.createStatement().executeQuery(sql);
			if (!rs.next()) return null;
			Pledge pledge = new Pledge(
				rs.getInt("id"),
				rs.getInt("donor_id"),
				rs.getString("description"), 
				rs.getString("amount"), 
				rs.getDate("begin_date"),
				rs.getDate("end_date"),
				rs.getInt("fulfilled") == 1 ? true : false,
				rs.getInt("cancelled") == 1 ? true : false,
				rs.getString("note"));
			return pledge;
		} catch (Exception e) {
			System.out.println("Error getting a pledge.");
			return null;
		}
	}
	
	public static boolean remove(int id) {
		try {
			Connection con = Util.getConnection();
			String sql = "DELETE FROM dm.pledge WHERE id = '" + id + "';";
			con.createStatement().execute(sql);
			return true;
		} catch (Exception e) {
			System.out.println("Error removing pledge: e");
			return false;
		}
	}
	
	public static boolean update(Pledge pledge) {
		try {
			Connection con = Util.getConnection();
			StringBuilder b = new StringBuilder("UPDATE dm.pledge ")
				.append("SET donor_id = ").append(pledge.getDonorId()).append(",")
				.append(" description = '").append(pledge.getDescription()).append("',")
				.append(" amount = '").append(pledge.getAmount()).append("',")
				.append(" begin_date = '").append(pledge.getBeginDate()).append("',")
				.append(" end_date = '").append(pledge.getEndDate()).append("',")
				.append(" fulfilled = ").append(pledge.isFulfilled()  ? 1 : 0).append(",")
				.append(" cancelled = ").append(pledge.isCancelled()  ? 1 : 0).append(" ")
				.append("WHERE id = ").append(pledge.getId()).append(";");
			String sql = b.toString();
			con.createStatement().execute(sql);
			return true;
		} catch (Exception e) {
			System.out.println("Error updating pledge: e");
			return false;
		}
	}
	
	public static Pledge add(Pledge pledge) {
		try {
			Connection con = Util.getConnection();
			StringBuilder b = new StringBuilder("INSERT INTO dm.pledge (id, donor_id, description, amount, fulfilled, cancelled, note) VALUES(")
				.append(pledge.getId()).append(",")
				.append("'").append(pledge.getDonorId()).append("',")
				.append("'").append(pledge.getDescription()).append("',")
				.append("'").append(pledge.getAmount()).append("',")
				.append("").append(pledge.isFulfilled() ? 1 : 0).append(",")
				.append("").append(pledge.isCancelled() ? 1 : 0).append(",")
				.append("'").append(pledge.getNote()).append("');");
			String sql = b.toString();
			con.createStatement().execute(sql);
			ResultSet rs = con.createStatement().executeQuery("SELECT LAST_INSERT_ID()");
			if (!rs.next()) return null;
			pledge.setId(rs.getInt(1));
			return pledge;
		} catch (Exception e) {
			System.out.println("Error adding pledge: e");
			return null;
		}
	}

	
}
