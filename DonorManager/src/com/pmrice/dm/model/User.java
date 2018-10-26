package com.pmrice.dm.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pmrice.dm.util.Util;

public class User {

	private String userid;
	private String password;
	private int admin;
	
	
	public User(String userid, String password, boolean admin) {
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
		Connection con = Util.getConnection();
		try {
			String sql = "SELECT * FROM user WHERE userid = '" + userid + "';";
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
			String sql = "DELETE FROM user WHERE userid = '" + userid + "';";
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
	public static boolean update(User user) {
		try {
			Connection con = Util.getConnection();
			String sql = "UPDATE user SET password = '" + user.password + "', admin = " + user.admin + " WHERE userid = '" + user.userid + "';";
			con.createStatement().execute(sql);
			return true;
		} catch (SQLException e) {
			System.out.println("Error updating a User");
			return false;
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
			String sql = "INSERT INTO user (userid, password, admin) VALUES('" + user.userid + "','" + user.password + "','" + user.admin + "');";
			con.createStatement().execute(sql);
			return true;
		} catch (SQLException e) {
			System.out.println("Error adding a User");
			return false;
		}
	}
	
	public static List<User> getUsers() {
		Connection con = Util.getConnection();
		try {
			String sql = "SELECT * FROM user ORDER BY userid;";
			ResultSet rs = con.createStatement().executeQuery(sql);
			List<User> users = new ArrayList<User>();
			while (rs.next()) {
					User user = new User(
					rs.getString("userid"), 
					rs.getString("password"), 
					rs.getBoolean("admin"));
					users.add(user);
			}
			return users;
		} catch (Exception e) {
			System.out.println("Error getting a User.");
			return null;
		}

	}
	
	public static boolean isUseridInUse(String userid) {
		Connection con = Util.getConnection();
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM user WHERE userid = '" + userid + "';");
			if (rs.next()) return true;
			else return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isUserValid(String userid, String password) {
		try {
			if (!User.isUseridInUse(userid)) return false;
			Connection connection = Util.getConnection();
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM user WHERE userid = '" + userid + "';");
			if (rs.next() == false) return false;
			return password.equals(rs.getString("password"));
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
}
