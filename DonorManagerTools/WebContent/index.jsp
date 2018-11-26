<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.io.*"%>
   <%
		File tomcatBin = new File(System.getProperty("user.dir"));
		File tomcatRoot = tomcatBin.getParentFile();
		File[] files = tomcatRoot.listFiles();
		File dbfiles = null;
		for (File file : files){
			if (file.getName().equals("dbfiles")){
				dbfiles = file;
				break;
			}
		}
		String[] dbs = new String[0];
  		if (dbfiles != null && dbfiles.isDirectory()){
   			dbs = dbfiles.list();
   		}else {
   			dbs = new String[]{"No directory", tomcatRoot.getName()};
   		}
   %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Donor Manager Tools</title>
</head>
<body>
<h2>Existing Organizations:</h2>
<ul>
	<% for (String id : dbs){ %>
	<li><%= id %></li>
	<%}%>
</ul>

<form action="tools" method="post">
	<input type="hidden" name="action" value="addOrg">
	<input type="text" name="orgid"><br>
	<input type="submit" value="Create new Organization">
</form>
	

</body>
</html>