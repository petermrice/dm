<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.pmrice.dm.model.*, java.util.*, java.sql.*"%>
<!DOCTYPE html>
<% 
	Connection con = (Connection)session.getAttribute("connection");
	List<Donor> donors =  Donor.getHiddenDonors(con); 
%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="dm.css">
	<style>
		#commit {
			border:line;
			color:Tomato;
			font-size: 16px;}		
			
	</style>
	<meta charset="UTF-8">
	<title>Manage Hidden Donors</title>
</head>
<body>
	<ul class="topmenu">
		<li class="topmenu_item">	
			<a href="main?action=show_donor">Donors</a>		
		</li>
	</ul>
<h2 class="title">Hidden Donor List</h2>

<div class="container">
<br>
<% if (donors.size() == 0) out.print("There are no hidden Donors.");%>
<ul>
<% for (Donor donor : donors){ %>
	<li><form action="main" >
		<%=donor.getName()%>
		<%=donor.getAddress1()%>
		<input type="checkbox" name="unhide" value="checked">Un-hide
		<input type="checkbox" name="delete" value="checked">Delete
		<input type="hidden" name="donor_id" value="<%=donor.getId()%>">
		<input type="hidden" name="action" value="manage_donor">
		<input type="submit" value="Submit" id="commit">
	</form></li>
<%}%>
</ul>
</div>
</body>
</html>