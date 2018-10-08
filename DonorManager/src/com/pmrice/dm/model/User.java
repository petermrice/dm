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
	
	public static User get(String userid) throws SQLException {
		Connection con = Util.getConnection();
		ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `user` WHERE `userid` = '" + userid + "';");
		if (rs.next()) {
			User user = new User(rs.getString("userid"), rs.getString("password"), rs.getInt("admin") == 1 ? true : false);
			return user;
		} else {
			return null;
		}
	}
	
	public static boolean remove(String userid) throws SQLException {
		Connection con = Util.getConnection();
		return con.createStatement().execute("REMOVE FROM user WHERE userid = '" + userid + "';");
	}
	
	public static boolean update(User user) throws SQLException {
		Connection con = Util.getConnection();
		return con.createStatement().execute(
				"UPDATE user SET password = '" + user.getPassword() + "' SET admin = " + user.admin + " + WHERE userid = '" + user.getUserid() + "';");
	}
	
	public static boolean add(User user) throws SQLException {
		Connection con = Util.getConnection();
		User u = get(user.getUserid());
		if (u == null) return con.createStatement().execute(
				"INSERT INTO user (userid, password, admin) VALUES('" + user.getUserid() + "','" + user.getPassword() + "','" + user.admin + "';");
		else return false;
	}
}
