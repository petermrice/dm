package com.pmrice.dm.library;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtils {
	
	/**
	 * Get the folder containing organization databases; 
	 * <tomcat home>/dbfiles
	 * 
	 * A null return indicates failure.
	 * 
	 * @return
	 */
	public static File getDbfiles() {
   		File tomcatBin = new File(System.getProperty("user.dir"));
   		File tomcatRoot = tomcatBin.getParentFile();
   		File[] files = tomcatRoot.listFiles();
   		File dbfiles = null;
   		for (File file : files){
   			if (file.getName().equals("dbfiles")){
   				return file;
   			}
   		}
   		return dbfiles;
	}
	
	/**
	 * When used in the DonorManager:
	 * 		this should be called only from the login, which caches the
	 * 		Connection object in the HttpSession for use during this session.
	 * 
	 * 		Check to be sure the database exists for this orgid
	 * 
	 * When used to create a database for an organization, check to be sure
	 * 		that one does not already exist.
	 * 
	 * Returns null if there is an error
	 * 
	 * @param orgid
	 * @return
	 */
	public static Connection getConnection(String orgid) {
		try {
			File dbfiles = getDbfiles();
			String url = "jdbc:hsqldb:file:" + dbfiles.getAbsolutePath() + "/" + orgid + "/dmdb";
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			Connection connection = DriverManager.getConnection(url, "SA", "PASS");
			connection.setAutoCommit(true);
			return connection;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
	public static boolean databaseExists(String orgid) {
		File[] files = getDbfiles().listFiles();
		for (File file : files) {
			if (file.getName().equals(orgid)) return true;
		}
		return false;
	}

}
