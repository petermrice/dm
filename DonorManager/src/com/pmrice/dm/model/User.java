package com.pmrice.dm.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pmrice.dm.util.Util;

public class User {

	private String userid;
	private String password;
	private int admin;
	
	
	public User(String userid, String password, boolean admin) {
		super();
		this.userid = userid;
		this.password = password;
		this.admin = admin ? 1 : 0;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return admin == 1 ? true : false;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin ? 1 : 0;
	}
	
	public static User get(String userid) {
		try {
			Connection con = Util.getConnection();
			String sql = "SELECT * FROM dm.user WHERE userid = '" + userid + "';";
			ResultSet rs = con.createStatement().executeQuery(sql);
			if (rs.next()) {
				User user = new User(rs.getString("userid"), rs.getString("password"), rs.getInt("admin") == 1 ? true : false);
				return user;
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Error getting a User");
			return null;
		}
	}
	
	public static boolean remove(String userid) {
		try {
			Connection con = Util.getConnection();
			String sql = "DELETE FROM dm.user WHERE userid = '" + userid + "';";
			con.createStatement().execute(sql);
			User u = get(userid);
			return u == null;
		} catch (SQLException e) {
			System.out.println("Error removing a User");
			return false;
		}
	}
	
	/**
	 * Testing for update requires pulling the result to see if the update worked.
	 * @param user
	 */
	public static void update(User user) {
		try {
			Connection con = Util.getConnection();
			String sql = "UPDATE dm.user SET password = '" + user.getPassword() + "', admin = " + user.admin + " WHERE userid = '" + user.getUserid() + "';";
			con.createStatement().execute(sql);
		} catch (SQLException e) {
			System.out.println("Error updating a User");
		}
	}
	
	/**
	 * Since the id of the User is the userid, the id is not generated
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public static boolean add(User user) {
		try {
			Connection con = Util.getConnection();
			User u = get(user.getUserid());
			if (u != null) return false;
			String sql = "INSERT INTO dm.user (userid, password, admin) VALUES('" + user.getUserid() + "','" + user.getPassword() + "','" + user.admin + "');";
			con.createStatement().execute(sql);
			return true;
		} catch (SQLException e) {
			System.out.println("Error adding a User");
			return false;
		}
	}
}
