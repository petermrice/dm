package com.prmice.dm.tools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;

import com.pmrice.dm.library.DatabaseUtils;

public class DBCreator {
	
	/**
	 * Create the db file at <tomkcat root>/dbfiles/folderName/dmdb
	 * 
	 * Read the DB_SCRIPT file, removing blank lines at the bottom.
	 * 
	 * Note: we assume the last line ends in a ";".
	 * 
	 * @param folderName
	 */
	public static void buildDb(String folderName) {
		
		File dbfiles = DatabaseUtils.getDbfiles();
		File[] files = dbfiles.listFiles();
		for (File file : files) {
			if (file.getName().equals(folderName)) return; // don't make a duplicate!
		}
		
		try {
			Connection con = DatabaseUtils.getConnection(folderName);
			Path path = Paths.get(System.getProperty("user.dir") + "/../webapps/DMTools/DB_SCRIPT");
			List<String> lines = Files.readAllLines(path);
			int count = lines.size();
			while (lines.get(count - 1).trim().length() == 0) --count;
			int i = 0;
			do {
				String command = "";
				String line = "";
				do {
					line = lines.get(i++);
					command = command.concat(line.trim());
				} while(!line.endsWith(";"));
				con.createStatement().executeUpdate(command);
			} while (i < count);
			con.close();
		} catch (Exception e) {
			System.err.println("DBCreator.buildDB: " + e);
		}

	}
}
