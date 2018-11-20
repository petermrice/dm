package com.pmrice.dm.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

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
	
	public static User get(Connection connection, String userid) {
		try {
			String sql = "SELECT * FROM dmuser WHERE userid = '" + userid + "';";
			ResultSet rs = connection.createStatement().executeQuery(sql);
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
	
	public static boolean remove(Connection connection, String userid) {
		try {
			String sql = "DELETE FROM dmuser WHERE userid = '" + userid + "';";
			connection.createStatement().execute(sql);
			User u = get(connection, userid);
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
	public static boolean update(Connection connection, User user) {
		try {
			String sql = "UPDATE dmuser SET password = '" + user.password + "', admin = " + user.admin + " WHERE userid = '" + user.userid + "';";
			connection.createStatement().execute(sql);
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
	public static boolean add(Connection connection, User user) {
		try {
			String sql = "INSERT INTO dmuser (userid, password, admin) VALUES('" + user.userid + "','" + user.password + "'," + user.admin + ");";
			connection.createStatement().execute(sql);
			return true;
		} catch (SQLException e) {
			System.out.println("Error adding a User: " + e);
			return false;
		}
	}
	
	public static List<User> getUsers(Connection connection) {
		try {
			String sql = "SELECT * FROM dmuser ORDER BY userid;";
			ResultSet rs = connection.createStatement().executeQuery(sql);
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
			System.out.println("Error getting a User: " + e);
			return null;
		}

	}
	
	public static boolean isUseridInUse(Connection connection, String userid) {
		try {
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM dmuser WHERE userid = '" + userid + "';");
			if (rs.next()) return true;
			else {
				System.err.println(userid + " is not a valid userid in the system.");
				return false;
			}
		} catch (Exception e) {
			System.err.println("In test of userid: " + e);
			return false;
		}
	}
	
	public static boolean isUserValid(Connection connection, String userid, String password) {
		try {
			if (!User.isUseridInUse(connection, userid)) return false;
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM dmuser WHERE userid = '" + userid + "';");
			if (rs.next() == false) return false;
			boolean passwordok = password.equals(rs.getString("password"));
			if (passwordok) return true;
			else {
				System.out.println(password + " is an incorrect password for userid");
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
}
