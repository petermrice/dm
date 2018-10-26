package com.pmrice.dm.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pmrice.dm.util.Currency;
import com.pmrice.dm.util.Util;

public class Donation implements Serializable {

	private static final long serialVersionUID = 3647899658839045729L;
	
	private int id;
	private int donor_id;
	private String date = "1970-00-00";
	private String description = "";
	private String amount = "0.00";
	private String note = "";
	
	public Donation() {
		
	}
	
	public Donation(int id, String date, String amount) {
		setId(id);
		setDate(date); 
		setAmount(amount);
	}
	
	public Donation(int id, String date, String description, String amount, String note) {
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
	
	/**
	 * Gets the date in display format
	 * @return
	 */
	public String getDate() {
		return date;
	}
	/**
	 * Saves the date in storage format
	 * @param date
	 */
	public void setDate(String date) {
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
	
	public String toString() {
		return this.getDate() + " $" + this.getAmount();
	}
		
	public static Donation get(int id)  {
		Connection con = Util.getConnection();
		try {
			String sql = "SELECT * FROM donation WHERE id = '" + id + "';";
			ResultSet rs = con.createStatement().executeQuery(sql);
			if (!rs.next()) return null;
			Donation donation = new Donation(
				rs.getInt("id"),
				Util.storageToDisplay(rs.getString("date")), 
				rs.getString("description"), 
				Currency.getDisplay(rs.getString("amount"), false), 
				rs.getString("note"));
			return donation;
		} catch (Exception e) {
			System.out.println("Error getting a Donation.");
			return null;
		}
	}
	
	/**
	 * Returns a list of Donation's for a specific Donor. Only the date and amount fields 
	 * are set. This is used only for display of the list in the browser.
	 * 
	 * @param donor_id
	 * @return
	 */
	public static List<Donation> getForDonor(int donor_id){
		List<Donation> list = new ArrayList<Donation>();
		Donation nd = new Donation();
		nd.setDonorId(donor_id);
		list.add(nd);
		Connection con = Util.getConnection();
		try {
			ResultSet rsc = con.createStatement().executeQuery("SELECT COUNT(*) FROM donation WHERE donor_id = '" + donor_id + "'");
			int count = 0;
			if (rsc.next()) count = rsc.getInt(1);
			if (count == 0) {
				return list; // otherwise the next query fails
			}
			String sql = "SELECT id, date, amount FROM donation WHERE donor_id = '" + donor_id + "' ORDER BY date DESC;";
			ResultSet rs = con.createStatement().executeQuery(sql);
			while(rs.next()) {
				Donation d = new Donation(
						rs.getInt("id"),
						Util.storageToDisplay(rs.getString("date")),
						Currency.getDisplay(rs.getString("amount"), false));
				list.add(d);
			}
			return list;
		} catch (SQLException e) {
			return list;
		}
	}
	
	public static boolean remove(int id) {
		try {
			Connection con = Util.getConnection();
			String sql = "DELETE FROM donation WHERE id = '" + id + "';";
			con.createStatement().execute(sql);
			return true;
		} catch (Exception e) {
			System.out.println("Error removing Donation: e");
			return false;
		}
	}
	
	public static boolean update(Donation donation) {
		Connection con = Util.getConnection();
		try {
			StringBuilder b = new StringBuilder("UPDATE donation ")
				.append("SET date = '").append(Util.displayToStorage(donation.getDate())).append("',")
				.append(" donor_id = ").append(donation.getDonorId()).append(",")
				.append(" description = '").append(donation.getDescription()).append("',")
				.append(" amount = '").append(Currency.store(donation.getAmount())).append("',")
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
		Connection con = Util.getConnection();
		try {
			Statement stmnt = con.createStatement();
			int newid = 0;
			ResultSet rs = stmnt.executeQuery("SELECT value FROM donationkey WHERE id = 1");
			if (rs.next()) newid = rs.getInt(1);
			stmnt.executeUpdate("UPDATE donationkey SET value = " + (newid + 1) + " WHERE id = 1;");
			donation.setId(newid);
			
			StringBuilder b = new StringBuilder("INSERT INTO donation (id, donor_id, date, description, amount, note) VALUES(")
				.append(newid).append(",")
				.append(donation.getDonorId()).append(",")
				.append("'").append(Util.displayToStorage(donation.getDate())).append("',")
				.append("'").append(donation.getDescription()).append("',")
				.append("").append(Currency.store(donation.getAmount())).append(",")
				.append("'").append(donation.getNote()).append("');");
			String sql = b.toString();
			con.createStatement().execute(sql);
			return donation;
		} catch (Exception e) {
			System.out.println("Error adding Donation: e");
			return null;
		}
	}


}
