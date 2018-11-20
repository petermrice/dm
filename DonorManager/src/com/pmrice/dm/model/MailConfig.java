package com.pmrice.dm.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MailConfig {

	private String url;
	private int port;
	private String userid;
	private String password;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
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
	
	public MailConfig() {}
	
	public static MailConfig get(Connection con) {
		try {
			String sql = "SELECT * FROM mailconfig WHERE id = 1";
			ResultSet rs = con.createStatement().executeQuery(sql);
			if (rs.next()) {
				MailConfig config = new MailConfig();
				config.setPassword(rs.getString("password"));
				config.setUserid(rs.getString("userid"));
				config.setUrl(rs.getString("url"));
				config.setPort(rs.getInt("port"));
				return config;
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Error getting the MailConfig");
			return null;
		}
	}
	
	public static void save(Connection con, MailConfig config) {
		try {
			String sql = "UPDATE mailconfig SET id = 1, url = '" + 
					config.getUrl() + "', port = " + config.getPort() + ", userid = '" + config.getUserid() +
					"', password = '" + config.getPassword() + "';";
			con.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Error getting the MailConfig");
		}
	}
	
	
}
